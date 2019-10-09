import javax.swing.*;

public class Main {

    public static void main(String args[])
    {
        try {
            // Set GUI look and feel to default System motif
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException e) {
            // this could be an anti-pattern ...
            try{
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
            catch(Exception a) {
                a.printStackTrace();
            }
        } catch (IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace();
        }


        // Create new GUI object and a thread for it.
        // Start thread and open GUI on start up.
        Gui test = new Gui();
        Thread thread1 = new Thread(test);
        thread1.start();
        test.createGUI();

    }

}
