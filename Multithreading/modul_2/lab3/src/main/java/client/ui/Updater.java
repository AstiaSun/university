package client.ui;

/**
 * Created by anastasia on 4/24/17.
 */
public class Updater implements Runnable {
    private MainFrame frame;
    public void setFrame(MainFrame frame) {
        this.frame = frame;
    }
    @Override
    public void run() {
        for (;;) {
            while (!MainFrame.dataWasChanged);
            frame.updateTables();
            MainFrame.dataWasChanged = false;
            if (frame == null) {
                return;
            }
        }
    }
}
