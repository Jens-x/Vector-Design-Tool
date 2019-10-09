import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileOperationTest {


    // NOT WORKING
//    @org.junit.jupiter.api.Test
//    void readFile() throws IOException {
//        Gui g = new Gui();
//        FileOperation FO = new FileOperation(g);
//        Tracker tracker = new Tracker();
//
//        String filename = "VEC file.vec";
//        FO.writeFile(filename, tracker);
//
//        FO.readFile(filename);
//    }


//     NOT WORKING
//    @org.junit.jupiter.api.Test
//    void readCommand() {
//        Gui g = new Gui();
//        FileOperation FO = new FileOperation(g);
//
//        FO.readCommand("LINE 0.1 0.5 0.2 0.1" );
//
//        assertTrue(g.getSelection() == 1 );
//
//
//    }

    /**
     * Call FileOperations method writeFile() then check if
     * a file has been created within the same directory with
     * the name VEC file.vec.
     * @throws IOException
     */
        @org.junit.jupiter.api.Test
    public void writeFile() throws IOException {
        // Initiate new Gui, FileOperation and Tracker objects
        Gui g = new Gui();
        FileOperation FO = new FileOperation(g);
        Tracker tracker = new Tracker();

        // Write a file name VEC file.vec
        String filename = "VEC file.vec";
        FO.writeFile(filename, tracker);

        // Assert that a file named VEC file.vec exists
        boolean exists = new File(filename).exists();
        assertTrue(exists);
    }
}