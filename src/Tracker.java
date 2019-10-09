import java.util.ArrayList;

public class Tracker {

    private ArrayList<String> commandBuffer;
    private ArrayList<String> undoBuffer;

    /**
     * The constructor for setting up command and undo lists
     */
    public Tracker(){
        commandBuffer = new ArrayList<>();
        undoBuffer = new ArrayList<>();
    }

    /**
     * Add the last command to the command buffer
     * @param command Command to be added to the buffer
     */
    public void addCommand(String command){
        commandBuffer.add(command);
    }

    /**
     * Returns the command list
     * @return The command list
     */
    public ArrayList<String> getCommand(){
        return commandBuffer;
    }

    /**
     * Returns the Undo list
     * @return The Undo list
     */
    public ArrayList<String> getUndo(){
        return undoBuffer;
    }

    /**
     * Undo the last command, and returns the command need to be drawn from the beginning
     *
     * It should be used like:
     * ArrayList<String> commands = undo();
     * clearCanvas();
     * FileOperation fileOP = new FileOperation();
     * (Loop through commands)
     * fileOP.readCommand(commands.get(i));
     *
     * Same for redo
     *
     * @return The command buffer so as to be redrawn
     */
    public ArrayList<String> undo(){

        if (commandBuffer.size() > 0) {
            undoBuffer.add(commandBuffer.get(commandBuffer.size() - 1));
            commandBuffer.remove(commandBuffer.size() - 1);
        }

        return commandBuffer;
    }

    /**
     * Redo the last undo command
     * @return The command to be redone
     */
    public String redo(){

        String redoCommand = null;
        if (undoBuffer.size() > 0) {
            redoCommand = undoBuffer.get(undoBuffer.size() - 1);
            undoBuffer.remove((undoBuffer.size() - 1));
        }

        return redoCommand;
    }

    public void clearCommands(){
        commandBuffer = new ArrayList<>();
    }

    /**
     * Clear the undo list once saved or new actions performed
     */
    public void clearUndo(){
        undoBuffer = new ArrayList<>();
    }

}
