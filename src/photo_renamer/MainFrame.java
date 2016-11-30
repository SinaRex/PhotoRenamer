package photo_renamer;

import backend.PhotoManager;
import backend.Photo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

public class MainFrame {

    public static PhotoManager photoManager;

    // MADE MODEL STATIC FOR UPdATING THE MAINFRAME WHEN EXITING THE IMAGEVIEWER FRAME!
    public static DefaultListModel<String> model;

    // MADE IT FOR Recurrence PHOTOS.
    public static List<Photo> listOfPhotos;
    /**
     * Create and return the window for the PhotoRenamer application.
     *
     * @return the main window frame for the MainFrame.
     */
    public static JFrame buildWindow() {

        JFrame mainFrame = new JFrame("PhotoRenamer");

        JFileChooser directoryChooser = new JFileChooser();
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //A label for letting the user now what to do.
        JLabel mainLabel = new JLabel("Select a directory");
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //The buttons on the frame
        JButton openButton = new JButton("Open Directory");
        JButton viewLogButton = new JButton("View Log");
        JButton revertAllButton = new JButton("Revert all");
        JButton showInfoButton = new JButton("Show Info.");
        revertAllButton.setEnabled(false);
        showInfoButton.setEnabled(false);

        JPanel optionButtonPanel = new JPanel();
        optionButtonPanel.add(openButton);
        optionButtonPanel.add(viewLogButton);
        optionButtonPanel.add(revertAllButton);
        optionButtonPanel.add(showInfoButton);


        //Will be used for list of images
        model = new DefaultListModel<>();
        JList<String> directoryList = new JList<>(model);
        JScrollPane dirListScrollPane = new JScrollPane(directoryList);
        directoryList.setFixedCellHeight(30);
        directoryList.setFixedCellWidth(500);
        directoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        //An ActionListener for openButton button.
        OpenButtonListener openListener = new OpenButtonListener(mainFrame, model, directoryChooser, mainLabel, showInfoButton, revertAllButton);
        openButton.addActionListener(openListener);

        //An ActionListener for view Log Button.
        viewLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame logViewer = new JFrame("Log Viewer");

                JTextArea logInfo = new JTextArea();
                logInfo.setEnabled(false);
                logInfo.setText(PhotoManager.logger.getLogInfo());

                JScrollPane textAreaScroll = new JScrollPane(logInfo);
                textAreaScroll.setPreferredSize(new Dimension(500, 500));

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        logViewer.dispose();
                    }
                });
                logViewer.setVisible(true);

                logViewer.add(textAreaScroll);
                logViewer.add(closeButton, BorderLayout.SOUTH);
                logViewer.pack();
            }
        });

        //An actionListener for revert button.
        revertAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                photoManager.revertAll();
                model.removeAllElements();
                for(Photo p : listOfPhotos) {
                    model.addElement(p.getTaggedName());
                }
                mainFrame.revalidate();
                mainFrame.repaint();
                photoManager.saveToFile();

            }
        });

        //A mouse listener for the JList directoryList.
        MouseListener selectionListener = new ItemSelectionListener(mainFrame, directoryList);
        directoryList.addMouseListener(selectionListener);

        //Add the components to the main frame
        mainFrame.add(mainLabel, BorderLayout.NORTH);
        mainFrame.add(dirListScrollPane, BorderLayout.CENTER);
        mainFrame.add(optionButtonPanel, BorderLayout.SOUTH);
        mainFrame.pack();
        return mainFrame;
    }


}
