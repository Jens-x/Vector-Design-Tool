import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Line extends Shape{
    private int x1, x2, y1, y2;
    private Gui gui;

    public Line(int x1, int y1, int x2, int y2, Gui gui){
        this. x1  = x1;
        this.y1 = y1;
        this.x2= x2;
        this.y2 = y2;
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
