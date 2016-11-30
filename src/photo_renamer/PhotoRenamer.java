package photo_renamer;

import javax.swing.*;
import java.awt.event.WindowListener;

/**
 * An application where you can add/remove tags with more features.
 */
public class PhotoRenamer {

    /**
     * This is where you run the file.
     * @param args the command line
     */
    public static void main(String[] args){
        JFrame mainFrame = MainFrame.buildWindow();
        mainFrame.setVisible(true);

        WindowListener mainWinListener = new MainWindowListener();
        mainFrame.addWindowListener(mainWinListener);

    }
}
