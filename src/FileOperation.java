import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileOperation {

    BufferedReader reader;
    BufferedWriter writer;
    Gui gui;
    /**
     * The constructor which does nothing
     */
    public FileOperation(Gui gui){
        this.gui = gui;
    }

    /**
     * Reads the file opened in the GUI
     * @param path The absolute path of the file to be opened
     */
    public void readFile(String path){
        try{
            reader = new BufferedReader((new FileReader(path)));
            String line = reader.readLine();
            while(line != null){
                this.readCommand(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads and performs a single line of command
     * @param line An array list of command split with space
     */
    public void readCommand(String line){

        String[] command = line.split("\\s+");

        if(command[0].equals("LINE")){
            gui.setSelection(1);
            gui.setCoordinate(Double.parseDouble(command[1]), Double.parseDouble(command[2]), Double.parseDouble(command[3]), Double.parseDouble(command[4]));
            gui.draw();
        }
        else if (command[0].equals("PLOT")){
            gui.setSelection(0);
            gui.setCoordinate(Double.parseDouble(command[1]), Double.parseDouble(command[2]), 0, 0);
            gui.draw();
        }
        else if (command[0].equals("RECTANGLE")){
            gui.setSelection(2);
            gui.setCoordinate(Double.parseDouble(command[1]), Double.parseDouble(command[2]), Double.parseDouble(command[3]), Double.parseDouble(command[4]));
            gui.draw();
        }
        else if (command[0].equals("ELLIPSE")){
            gui.setSelection(3);
            gui.setCoordinate(Double.parseDouble(command[1]), Double.parseDouble(command[2]), Double.parseDouble(command[3]), Double.parseDouble(command[4]));
            gui.draw();
        }
        else if (command[0].equals("POLYGON")){
            gui.setSelection(4);
            double x = 0;
            double y = 0;
            for (int i = 1; i < command.length; i++){
                if ( (i & 1) == 0 ){
                    // If i is even, it's y coordinate
                    y = Double.parseDouble(command[i]);
                    gui.setCoordinate(x, y, 0, 0);
                    gui.drawPolygon();
                }
                else{
                    // If i is odd, it's x coordinate
                    x =Double.parseDouble(command[i]);
                }
            }
            gui.finishPolygon();
        }
        else if (command[0].equals("FILL")){
            if(!command[1].equals("OFF")) {
                gui.setFill(true);
                Color fillColour = Color.decode(command[1]);
                gui.setFillColour(fillColour);
            }
            else{
                gui.setFill(false);
            }
        }
        else if (command[0].equals("PEN")){
            Color penColour = Color.decode(command[1]);
            gui.setPenColour(penColour);
        }

    }

    /**
     * Save the commands into file
     * @param path The absolute path of the file to be saved
     * @throws IOException
     */
    public void writeFile(String path, Tracker tracker) throws IOException {

        ArrayList<String> commands = tracker.getCommand();

        writer = new BufferedWriter(new FileWriter(path));
        for (int i = 0; i < commands.size(); i++){
            writer.write(commands.get(i) + "\n");
        }

        writer.close();
    }
}
