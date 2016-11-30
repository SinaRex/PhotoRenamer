package photo_renamer;

import backend.Photo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RemoveButtonListener implements ActionListener{
    private TagCheckBoxPanel tagCheckBoxPanel;
    private Photo photo;


    public RemoveButtonListener(TagCheckBoxPanel tcb, Photo photo) {
        this.tagCheckBoxPanel = tcb;
        this.photo = photo;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        List<String> selectedBoxes = this.tagCheckBoxPanel.getSelected();
        for(String label : selectedBoxes) {
            photo.remove(label);
            this.tagCheckBoxPanel.removeCheckBox(label);
            MainFrame.model.removeElementAt(ItemSelectionListener.index);
            MainFrame.model.insertElementAt(photo.getTaggedName(), ItemSelectionListener.index);
        }
        this.tagCheckBoxPanel.revalidate();
        this.tagCheckBoxPanel.repaint();

    }
}
