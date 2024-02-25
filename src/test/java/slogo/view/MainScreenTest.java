package slogo.view;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.control.Button;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import slogo.Controller;
import util.DukeApplicationTest;

public class MainScreenTest extends DukeApplicationTest {
  // keep only if needed to call application methods in tests
  private View myView;
  // not tested yet
  private TextInputControl myTextField;
  private Button mySubmitButton;
  private Button myTestButton;


  // this method is run BEFORE EACH test to set up application in a fresh state
  @Override
  public void start (Stage stage) {
    // create application and add scene for testing to given stage
    try {
      Controller.openNewSession();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    // the 0 is the id set when creating FrontEndTurtle
    // components that will be reused in different tests
    myTextField = lookup("#field").query();
    // clear text field, just in case
    myTextField.clear();

    mySubmitButton = lookup("#submit").query();
    myTestButton = lookup("#test").query();
  }

  @Test
  void testTextFieldAction () {
    String expected = "ENTER test!";
    // GIVEN, app first starts up
    // WHEN, command is typed and action is activated with ENTER key
    writeInputTo(myTextField, expected + "\n");
    // THEN, TODO what should happen?
    assertEquals("ENTER test!", myTextField.getText());
  }

  @Test
  void testSubmitButtonAction () {
    String expected = "fd 50";
    // GIVEN, app first starts up
    // WHEN, command is typed and action is activated with ENTER key
    writeInputTo(myTextField, expected + "\n");
    clickOn(mySubmitButton);
    // THEN, TODO what should happen?
    // probably check if model turtle moved or turtle node moved
    assertEquals("", myTextField.getText());
  }

  @Test
  void testTestButtonAction () {
    // GIVEN, app first starts up
    // WHEN, command is typed and action is activated with ENTER key
    clickOn(myTestButton);
    // THEN, TODO what should happen?
    // I understood it to move turtle to 100,100, I do not know how to check turtle moved to that position
    sleep(1, TimeUnit.SECONDS);
    ImageView myTurtleView = lookup("#turtle").query();
    //these fail because there are multiple turtled with same ID, I tried to see how to work around it
    //but I did not want to change too much of the code
    assertEquals(myTurtleView.getX(), 100);
    assertEquals(myTurtleView.getY(), 100);
  }



}
