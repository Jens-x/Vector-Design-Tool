import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    Gui newGui;

    /**
     * setting up the new gui before each test
     */
    @BeforeEach
    void setUp() {
        newGui = new Gui();
    }

    /**
     * creating the new Line object and making sure that its fields are as expected, therefore
     * verifying the successful creation of the object.
     */
    @Test
    void getDimensions(){
        Line line2 = new Line(2,3,5,3, newGui);
        assertEquals(2, line2.getX1());
        assertEquals(3, line2.getY1());
    }
}