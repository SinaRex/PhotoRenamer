package photo_renamer;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class MainWindowListener implements WindowListener {

    @Override
    public void windowOpened(WindowEvent e) {
    }

    /**
     * Helps the PhotoRenamer to close properly.
     *
     * @param e closing event is passed in, so that we can close
     *          the window.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        if (MainFrame.photoManager != null) {
            MainFrame.photoManager.saveToFile();
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
