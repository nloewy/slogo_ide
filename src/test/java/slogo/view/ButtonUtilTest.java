package slogo.view;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Feel free to completely change this code or delete it entirely.
 */
class ButtonUtilTest {
    private View myApp;

    // create new instance of test object before each test is run
    @BeforeEach
    void setup () {

    }

    @Test
    void testRunThrows () {
        Button newButton = ButtonUtil.generateButton("test", 0, 0, (event)->{});
    }
}
