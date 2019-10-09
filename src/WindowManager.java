import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowManager implements WindowListener
{
    private int windowsOpen;

    public int getWindowsOpen()
    {
        return windowsOpen;
    }

    public void setWindowsOpen(int windowsOpen)
    {
        this.windowsOpen = windowsOpen;
    }

    @Override
    public void windowOpened(WindowEvent e)
    {
        windowsOpen++;
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        if (windowsOpen <= 1){
            System.exit(0);
        }
        windowsOpen--;
    }

    @Override
    public void windowClosed(WindowEvent e)
    {

    }

    @Override
    public void windowIconified(WindowEvent e)
    {

    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {

    }

    @Override
    public void windowActivated(WindowEvent e)
    {

    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {

    }
}
