import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlotTest {

    Gui newGui;

    /**
     * setting up the new gui before each test
     */
    @BeforeEach
    void setUp() {
        newGui = new Gui();
    }

    /**
     * creating the new Plot object and making sure that its fields are as expected, therefore
     * verifying the successful creation of the object.
     */
    @Test
    void getDimensions(){
        Plot plot2 = new Plot (2,3,5,3, newGui);
        assertEquals(2, plot2.getX1());
        assertEquals(3, plot2.getY1());
    }
}