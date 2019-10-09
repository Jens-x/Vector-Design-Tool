import java.awt.*;
import java.util.ArrayList;

public class Poly extends Shape{

    private ArrayList<String> coordinates;
    private Gui gui;
    private Polygon polygon;

    public Poly(Gui gui){
        this.gui = gui;
        coordinates = new ArrayList<String>();
        polygon = new Polygon();
    }

    public void DrawRectabgle(int x, int y){

    }

    public void addCoordinate(int x, int y){
        // Add the coordinate to be recorded

        int canvasHeight = gui.guiGetWidth();
        int canvasWidth = gui.guiGetHeight();

        // Calculate percentages of x1, y1, x2, y2, w and h relative to the canvas dimensions
        double x1Percent = (double)x / (double)canvasWidth;
        double y1Percent = (double)y / (double)canvasHeight;

        // Round the coordinate values to 2 decimal places (one decimal place will show 0.1 not 0.10)
        x1Percent = Math.round(x1Percent * 100) / 100.0;
        y1Percent = Math.round(y1Percent * 100) / 100.0;

        coordinates.add(x1Percent + " " + y1Percent);
        // Add coordinate to polygon
        polygon.addPoint(x, y);
    }

    public Polygon getPoly(){
        return polygon;
    }

    public void clearCoordinates(){
        coordinates = new ArrayList<>();
    }

    public ArrayList<String> getCoordinates(){
        return coordinates;
    }

    @Override
    int getX1() {
        return 0;
    }

    @Override
    int getY1() {
        return 0;
    }
}
