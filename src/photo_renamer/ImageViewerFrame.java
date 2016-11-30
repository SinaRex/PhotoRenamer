package photo_renamer;

import backend.Photo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * A window frame when mainframe's open button is clicked.
 */
public class ImageViewerFrame {
    /**
     * Create and return the image viewer window when a photo is selected.
     *
     * @return the main window frame for the MainFrame.
     */
    public static JFrame buildWindow(Photo photo, int index) {

        // A frame or window with "Image Viewer" title.
        JFrame imageViewer = new JFrame(photo.getTaggedName());

        // The image panel for a resized image to be placed in.
        JPanel imagePanel = new JPanel();
        Dimension imagePanelSize = new Dimension(640, 480);
        imagePanel.setPreferredSize(imagePanelSize);

        // Opening and adding resized image to the image panel.
        BufferedImage img;
        try {

            // Reading an image.
            img = ImageIO.read(new File(photo.getFilePhoto().getAbsolutePath()));

            // Scaling down the image.
            Image scaledImage  = img.getScaledInstance((int) imagePanelSize.getWidth() - 40,
                    (int) imagePanelSize.getHeight() - 40, Image.SCALE_SMOOTH);

            // Adding it to the image panel and the frame.
            ImageIcon icon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(null, icon, JLabel.CENTER);
            imagePanel.add(imageLabel);
            imagePanel.setBorder(BorderFactory.createRaisedBevelBorder());
            imageViewer.add(imagePanel, BorderLayout.CENTER);

        } catch (IOException e) {
            e.getMessage();
        }

        // Master Panel that contains button panel and check box panel.
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout(0, 0));

        // Label for the master Panel
        JLabel containerLabel = new JLabel("List of Tags of this photo.");

        // Panel for buttons. It is placed on the SOUTH.
        JPanel optionsButtonPanel = new JPanel();

        // Panel for removing.
        JPanel removePanel = new JPanel();
        removePanel.setLayout(new BorderLayout(0, 0));

        // Check Box Panel
        TagCheckBoxPanel checkBoxPanel = new TagCheckBoxPanel(photo);
        checkBoxPanel.showPhotoTags();
        JScrollPane checkBoxScroll = new JScrollPane(checkBoxPanel);
        checkBoxPanel.setLayout(new GridLayout(0, 2));
        checkBoxScroll.setPreferredSize(new Dimension(10, 100));
        //Remove Button for the removePanel
        JButton removeButton = new JButton("Remove");
        RemoveButtonListener removeButtonListener = new RemoveButtonListener(checkBoxPanel, photo);
        removeButton.addActionListener(removeButtonListener);

        removePanel.add(checkBoxScroll, BorderLayout.CENTER);
        removePanel.add(removeButton, BorderLayout.SOUTH);

        JButton addTagsButton = new JButton("Add Tags");
        JButton revertButton = new JButton("Revert To Date");
        JButton usePrevName = new JButton("Use Previous Name");

        optionsButtonPanel.add(addTagsButton, BorderLayout.SOUTH);
        optionsButtonPanel.add(revertButton, BorderLayout.SOUTH);
        optionsButtonPanel.add(usePrevName, BorderLayout.SOUTH);

        containerPanel.add(containerLabel, BorderLayout.NORTH);
        containerPanel.add(optionsButtonPanel, BorderLayout.SOUTH);
        containerPanel.add(removePanel, BorderLayout.CENTER);


        //An action listeners for add button, remove button, and revert button.
        ActionListener addTagsButtonListener =  new AddTagsButtonListener(imageViewer, containerPanel,
                removePanel, optionsButtonPanel, removeButton, checkBoxPanel, containerLabel,photo);
        addTagsButton.addActionListener(addTagsButtonListener);


        /*
          A listener for back button on the imageViewer frame, which it reverts every photo
          to their original state.
         */
        revertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel<String> model = new DefaultListModel<>();
                List<Date> dates = photo.getDatesSorted();
                for(Date d: dates) {
                    model.addElement("(" + photo.getDateToNames().get(d) + ")--" + d.toString());
                }

                JList<String> dateList = new JList<>(model);

                JScrollPane dateListScrollPane = new JScrollPane(dateList);
                dateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                dateListScrollPane.setPreferredSize(new Dimension(400, 100));

                JButton revertToThisButton = new JButton("Revert to This or Go Back");

                // a listener for this button which it saves the contents of the panel and
                // brings the user back to the previous state.
                revertToThisButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (dateList.getSelectedIndex() >= 0) {
                            Date selectedDate = dates.get(dateList.getSelectedIndex());
                            photo.revertToDate(selectedDate);
                        }

                        // update the frame by removing and adding (AKA. refreshing)
                        containerPanel.remove(dateListScrollPane);
                        containerPanel.remove(revertToThisButton);
                        checkBoxPanel.removeAll();
                        checkBoxPanel.showPhotoTags();
                        containerPanel.add(optionsButtonPanel, BorderLayout.SOUTH);
                        containerPanel.add(removePanel, BorderLayout.CENTER);

                        imageViewer.pack();

                        MainFrame.model.removeElementAt(ItemSelectionListener.index);
                        MainFrame.model.insertElementAt(photo.getTaggedName(), ItemSelectionListener.index);

                    }
                });


                containerPanel.remove(optionsButtonPanel);
                containerPanel.remove(removePanel);
                containerPanel.add(dateListScrollPane, BorderLayout.CENTER);
                containerPanel.add(revertToThisButton, BorderLayout.SOUTH);
                imageViewer.pack();
            }
        });

        /* A actionListener for this use previous which undos every
        change since beginnings photo's state.*/
        usePrevName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (photo.getDatesSorted().size() >= 2) {
                    photo.revertToDate(photo.getDatesSorted().get(photo.getDatesSorted().size() - 2));

                    checkBoxPanel.removeAll();
                    checkBoxPanel.showPhotoTags();
                    checkBoxPanel.revalidate();
                    checkBoxPanel.repaint();

                    MainFrame.model.removeElementAt(ItemSelectionListener.index);
                    MainFrame.model.insertElementAt(photo.getTaggedName(), ItemSelectionListener.index);


                } else {
                    JOptionPane.showMessageDialog(imageViewer, "There are no previous " +
                            "names in the record!", "Message", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        imageViewer.add(containerPanel, BorderLayout.WEST);
        imageViewer.pack();

        return imageViewer;
    }

}
