package photo_renamer;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class ImageViewerWindowListener extends MainWindowListener{
    private JFrame mainFrame;
    private JFrame imageViewer;

    public ImageViewerWindowListener(JFrame mainFrame, JFrame iv){
        this.mainFrame = mainFrame;
        this.imageViewer = iv;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.mainFrame.setEnabled(true);
        MainFrame.photoManager.saveToFile();
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
        // this.mainFrame.pack();
        this.imageViewer.dispose();

    }
}
