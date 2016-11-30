package photo_renamer;

import backend.Photo;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ItemSelectionListener implements MouseListener {

    /** The main window where the button is in. */
    private JFrame mainFrame;
    /** The list of images to select from on the main window. */
    private JList<String> imageList;
    /** The index of selected item on the JList.*/
    public static int index;

    /**
     *
     * @param mainFrame the main window where the button is in.
     * @param imageList the list of images to select from on the main window.
     */
    public ItemSelectionListener(JFrame mainFrame, JList<String> imageList) {
        this.mainFrame = mainFrame;
        this.imageList = imageList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getClickCount() == 2){
            // System.out.println(this.imageList.getSelectedIndex());
            // Photo photo = MainFrame.photoManager.lookForPhoto(this.imageList.getSelectedValue());
            Photo photo = MainFrame.listOfPhotos.get(this.imageList.getSelectedIndex());
            index = this.imageList.getSelectedIndex();

            JFrame imageViewer = ImageViewerFrame.buildWindow(photo, this.imageList.getSelectedIndex());
            imageViewer.setVisible(true);

            ImageViewerWindowListener imageViewerWindowListener = new ImageViewerWindowListener(this.mainFrame, imageViewer);
            imageViewer.addWindowListener(imageViewerWindowListener);

            //Make the main frame un-clickable.
            this.mainFrame.setEnabled(false);


        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
