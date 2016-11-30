package photo_renamer;

import backend.Photo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * A listener for add tag button in the imageViewer frame of this program.
 */
public class AddTagsButtonListener implements ActionListener {

    /** The frame where it holds an image and list of tags on the left*/
    private JFrame imageViewer;

    /** These are nested panels where containerPanel is the master,
     * checkBoxPanel is in the center, and optionButtonPanle is in the
     * south of containerPanel*/
    private JPanel containerPanel, checkBoxPanel, optionsButtonPanel;

    /** The button for removing list of tags.*/
    private JButton removeButton;

    /** The panel where it contains list of checkBox*/
    private TagCheckBoxPanel currentTagsCheckBox;

    /** The photo where we are working with*/
    private Photo photo;

    /** the label for the master panel which is containerPanel*/
    private JLabel containerLabel;

    /**
     * A listener for the add tag button on the imageViewer frame.
     *
     * @param iv the frame where it holds an image and list of tags on the left
     * @param cp the master panel
     * @param rp a panel inside the center of master panel
     * @param obp a panel inside the south region of the master panel
     * @param rb the remove button in the removePanel.
     * @param cbp the checkBox panel which contains all the checkBoxes
     * @param cl the label for to guide the user
     * @param photo the photo that is being worked on
     */
    public AddTagsButtonListener(JFrame iv, JPanel cp, JPanel rp, JPanel obp, JButton rb, TagCheckBoxPanel cbp, JLabel cl,Photo photo) {
        this.imageViewer = iv;
        this.containerPanel = cp;
        this.checkBoxPanel = rp;
        this.optionsButtonPanel = obp;
        this.removeButton = rb;
        this.currentTagsCheckBox =  cbp;
        this.photo = photo;
        this.containerLabel = cl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.containerLabel.setText("All used current-existing tags.");
        this.containerPanel.remove(this.optionsButtonPanel);
        this.checkBoxPanel.remove(this.removeButton);
        this.currentTagsCheckBox.removeAll();
        this.currentTagsCheckBox.showCurrentUsedTags();

        // the panel that hold save and back button
        JPanel newButtonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back");
        newButtonPanel.add(saveButton, BorderLayout.SOUTH);
        newButtonPanel.add(backButton, BorderLayout.SOUTH);

        // the panel where it holds the text and the add button.
        JPanel textAddingPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JTextField textField = new JTextField();
        textField.setColumns(28);
        textAddingPanel.add(textField);
        textAddingPanel.add(addButton);

        this.checkBoxPanel.add(textAddingPanel, BorderLayout.SOUTH);
        this.containerPanel.add(newButtonPanel, BorderLayout.SOUTH);


        /**
         * A listener for back button on the imageViewer frame, which in takes the user
         * back to previous state of the frame.
         */
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                containerLabel.setText("List of Tags of this photo.");
                containerPanel.remove(newButtonPanel);
                checkBoxPanel.remove(textAddingPanel);
                currentTagsCheckBox.deselectAll();
                currentTagsCheckBox.removeAll();
                currentTagsCheckBox.showPhotoTags();
                checkBoxPanel.add(removeButton, BorderLayout.SOUTH);
                containerPanel.add(optionsButtonPanel, BorderLayout.SOUTH);

                imageViewer.pack();
            }
        });

        /**
         * A listener for add button on the imageViewer frame. Adds
         * the contents of the textbox to the list of tags.
         */
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textField.getText().equals("")) {
                    photo.add(textField.getText());
                    currentTagsCheckBox.removeAll();
                    currentTagsCheckBox.showCurrentUsedTags();
                    currentTagsCheckBox.revalidate();
                    currentTagsCheckBox.repaint();

                    updateMainFrame();

                }
            }
        });

        /**
         * A listener for add button on the imageViewer frame. Saves every selected checkBox's labels
         * to the list of tags of this photo.
         */
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> labels = currentTagsCheckBox.getSelected();
                for (String l : labels) {
                    photo.add(l);
                    currentTagsCheckBox.removeAll();
                    currentTagsCheckBox.showCurrentUsedTags();
                    currentTagsCheckBox.revalidate();
                    currentTagsCheckBox.repaint();

                    updateMainFrame();
                }
            }
        });
        this.imageViewer.pack();
    }

    /**
     * update the main frame with the new contents
     */
    private void updateMainFrame (){
        MainFrame.model.removeElementAt(ItemSelectionListener.index);
        MainFrame.model.insertElementAt(photo.getTaggedName(), ItemSelectionListener.index);
    }
}
