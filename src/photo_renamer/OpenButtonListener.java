package photo_renamer;

import javax.swing.*;
import backend.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class OpenButtonListener implements ActionListener {


    /** The main window where the button is in. */
    private JFrame mainFrame;
    /** The container to contain list of images*/
    private DefaultListModel<String> images;
    /** The file chooser to use the path when the user chooses a directory*/
    private JFileChooser fileChooser;
    /** The label for the users to know that they need to choose an image.*/
    private JLabel mainLabel;
    /** The button for showing the additional features for PhotoRenamer application.*/
    private JButton showInfoButton;
    /** The button for reverting all current-existing photos to their original status.*/
    private final JButton revertToAllButton;
    /**
     * An action listener for window frame, adding list of images
     * to model by using fileChooser to choose a file.
     *
     * @param frame the main frame
     * @param model the container for list of images and directories.
     */
    public OpenButtonListener(JFrame frame, DefaultListModel<String> model, JFileChooser dirChooser, JLabel label, JButton showInfoB, JButton revertBut) {
        this.mainFrame = frame;
        this.images = model;
        this.fileChooser = dirChooser;
        this.mainLabel = label;
        this.showInfoButton = showInfoB;
        this.revertToAllButton = revertBut;
    }

    /**
     * Handle the user clicking on the open button.
     *
     * @param e he event object
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        int returnValue = this.fileChooser.showOpenDialog(mainFrame);

        if (returnValue == JFileChooser.APPROVE_OPTION){

            this.images.removeAllElements();

            File file =  this.fileChooser.getSelectedFile();
            if (file.exists()){
                mainLabel.setText("Directory is loaded! Double click on a Photo to edit");
                mainLabel.setHorizontalAlignment(SwingConstants.LEFT);
                PhotoManager photoManager = new PhotoManager(file);
                photoManager.openDirectory();

                MainFrame.listOfPhotos = new ArrayList<>();
                photoManager.makeListOfPhotos(MainFrame.listOfPhotos, file);
                for(Photo p : MainFrame.listOfPhotos) {
                    this.images.addElement(p.getTaggedName());
                }
                MainFrame.photoManager = photoManager;

                showInfoButton.setEnabled(true);
                revertToAllButton.setEnabled(true);

                showInfoButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showInfoButton.setText("Refresh Info.");
                        JPanel infoPanel = new JPanel();
                        infoPanel.setLayout(new BorderLayout(0, 0));
                        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        JPanel labelsPanel = new JPanel(new GridLayout(0, 1));

                        JLabel infoLabel = new JLabel("<HTML><STRONG><U>Information</U></STRONG><HTML>");
                        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        JLabel commonTagLabel = new JLabel("The most commmon tag is: " + photoManager.getMostCommonTag());

                        JLabel mostTaggedPhotoLabel;
                        if (photoManager.getMostTaggedPhoto() == null) {
                            mostTaggedPhotoLabel = new JLabel("The most tagged photo is: " + "<<None>>");
                        } else {
                            mostTaggedPhotoLabel = new JLabel("The most tagged " +
                                    "photo is: " + photoManager.getMostTaggedPhoto().getTaggedName());
                        }


                        labelsPanel.add(commonTagLabel);
                        labelsPanel.add(mostTaggedPhotoLabel);
                        JScrollPane scrollPanel = new JScrollPane(labelsPanel);
                        scrollPanel.setPreferredSize(new Dimension(250, 150));

                        infoPanel.add(infoLabel, BorderLayout.NORTH);
                        infoPanel.add(scrollPanel, BorderLayout.CENTER);

                        mainFrame.add(infoPanel, BorderLayout.EAST);
                        mainFrame.revalidate();
                        mainFrame.repaint();
                        mainFrame.pack();

                        showInfoButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                mainFrame.remove(infoPanel);
                            }
                        });
                    }
                });

                this.mainFrame.revalidate();
                this.mainFrame.repaint();
                this.mainFrame.pack();

            }
            if(this.images.size() == 0) {
                mainLabel.setText("No images in this directory.");
            }
        } else {
            mainLabel.setText("No directory was selected.");
        }

    }

}

