import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Plot extends Shape{
    int x1,y1, width, height;
    private Gui gui;

    public Plot(int x1, int y1, int width, int height, Gui gui){
        this. x1  = x1;
        this.y1 = y1;
        this.width = width;
        this.height = height;
        this.gui = gui;
    }

    @Override
    int getX1() {
        return x1;
    }

    @Override
    int getY1() {
        return y1;
    }
}
