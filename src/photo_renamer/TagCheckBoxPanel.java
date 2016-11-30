package photo_renamer;


import backend.Photo;
import backend.Tag;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The TagCheckBoxPanel with the Iterator design pattern.
 *
 */
public class TagCheckBoxPanel extends JPanel implements Iterable<JCheckBox> {

    /** An array list of check boxes which each element represents a tag*/
    private List<JCheckBox> tagsBoxes;
    /** The photo that was selected from the mainFrame to view the list of tags*/
    private Photo photo;

    /**
     * The panel which hold list of checkboxes which represent tags with the given photo Photo.
     * @param photo the photo that was selected from the mainFrame to view the list of tags
     */
    public TagCheckBoxPanel(Photo photo) {
        this.photo = photo;
        this.tagsBoxes = new ArrayList<>();
    }


    @Override
    public void remove(Component comp) {
        super.remove(comp);
    }

    @Override
    public Component add(Component comp) {
        this.tagsBoxes.add((JCheckBox) comp);
        return super.add(comp);
    }
    /**
     * Remove the input label from the current Checkbos Panel.
     * @param label
     */
    public void removeCheckBox(String label) {
        List<JCheckBox> copy = new ArrayList<>();
        copy.addAll(this.tagsBoxes);
        for(JCheckBox cb : copy) {
            if (cb.getText().equals(label)) {
                this.tagsBoxes.remove(cb);
                this.remove(cb);
            }
        }

    }

    /**
     * Return List of labels which get selected in the CheckboxPanel.
     *
     * @return List of labels which get selected in the CheckboxPanel.
     */
    public List<String> getSelected() {
        ArrayList<String> labels = new ArrayList<>();

        Iterator<JCheckBox> checkBox0 = this.iterator();
        while(checkBox0.hasNext()) {
            JCheckBox tb = checkBox0.next();
            if (tb.isSelected()) {
                labels.add(tb.getText());
            }
        }
        return labels;
    }
    /**
     * Deselect all the checkboxs within the Panel.
     */
    public void deselectAll() {
        Iterator<JCheckBox> checkBox0 = this.iterator();
        while(checkBox0.hasNext()) {
            JCheckBox tb = checkBox0.next();
            tb.setSelected(false);
        }
    }
    /**
     * Show the all Photo tags in the Checkbox Panel.
     */
    public void showPhotoTags() {
        for(Tag t: photo.getTags()){
            JCheckBox tagCheckBox = new JCheckBox(t.label);
            this.add(tagCheckBox);
        }
    }
    /**
     * Show all current used photo tags in the Checkbox Panel is set the ones
     * that the photo already poccess to false.
     */
    public void showCurrentUsedTags() {
        ArrayList<String> allTags = MainFrame.photoManager.getAllCurrentTags();

        ArrayList<String> photoTagsLabel = new ArrayList<>();
        for (Tag t : photo.getTags()) {
            photoTagsLabel.add(t.label);
        }

        for (String label : allTags) {
            JCheckBox tagCheckBox = new JCheckBox(label);
            this.add(tagCheckBox);
            if (photoTagsLabel.contains(label)) {
                tagCheckBox.setEnabled(false);
            }
        }
    }

    /**
     * Returns an iterator for this address book.
     *
     * @return an iterator for this address book.
     */
    @Override
    public Iterator<JCheckBox> iterator() {
        return new TagCheckBoxPanelIterator();
    }

    /**
     * An Iterator for TagCheckBoxPanel checkboxes.
     */
    private class TagCheckBoxPanelIterator implements Iterator<JCheckBox> {

        /** The index of the next tagsBoxes to return. */
        private int current = 0;

        /**
         * Returns whether there is another JCheckBox to return.
         * @return whether there is another JCheckBox to return.
         */
        @Override
        public boolean hasNext() {
            return current < tagsBoxes.size();
        }

        @Override
        public JCheckBox next() {
            JCheckBox checkBox;
            try {
                checkBox = tagsBoxes.get(current);
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
            current += 1;
            return checkBox;
        }
    }
}
