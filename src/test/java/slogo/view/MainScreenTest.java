package slogo.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class MainScreenTest extends DukeApplicationTest {

  private Button submitButton;
  private TextField commandField;
  private ImageView myTurtleView;
  private Pane centerPane;
  private double centerX;
  private double centerY;

  @Override
  public void start(Stage stage) throws FileNotFoundException {
    stage.setTitle("SLogo");
    try {
      Controller controller = new Controller(stage);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Button loadGen = lookup("#LoadGen").query();
    clickOn(loadGen);

  }

  @BeforeEach
  public void setUpMainScreenItems() {
    // Now you can interact with the main screen's components
    submitButton = lookup("#Submit").query();
    commandField = lookup("#CommandField").query();
    myTurtleView = lookup("#turtle").query();
    centerPane = lookup("#CenterPane").query();
    centerX = centerPane.getWidth() / 2;
    centerY = centerPane.getHeight() / 2;
  }

  @Test
  void testDefaultStart() {
    assertEquals(centerX, myTurtleView.getLayoutX(), 0.01);
    assertEquals(centerY, myTurtleView.getLayoutY(), 0.01);
  }

  @Test
  void testSubmitButtonAction() {
    String expected = "fd 50";
    writeInputTo(commandField, expected);
    clickOn(submitButton);
    sleep(600, TimeUnit.MILLISECONDS);
    assertEquals(centerY - 50, myTurtleView.getLayoutY(), 0.01);
    assertEquals(centerX, myTurtleView.getLayoutX(), 0.01);
  }

  @Test
  void testTurtleMoveButtonsForward() {
    Button forwardButton = lookup("#↑").query();
    clickOn(forwardButton);
    sleep(600, TimeUnit.MILLISECONDS);
    assertEquals(centerY - 50, myTurtleView.getLayoutY(), 0.01);
    assertEquals(centerX, myTurtleView.getLayoutX(), 0.01);
  }

  @Test
  void testTurtleMoveButtonsBackward() {
    Button backwardButton = lookup("#↓").query();
    clickOn(backwardButton);
    sleep(600, TimeUnit.MILLISECONDS);
    assertEquals(centerY + 50, myTurtleView.getLayoutY(), 0.01);
    assertEquals(centerX, myTurtleView.getLayoutX(), 0.01);
  }

  @Test
  void testTurtleMoveButtonsLeft() {
    Button leftButton = lookup("#←").query();
    clickOn(leftButton);
    sleep(200, TimeUnit.MILLISECONDS);
    assertEquals(centerY, myTurtleView.getLayoutY(), 0.01);
    assertEquals(centerX, myTurtleView.getLayoutX(), 0.01);

    Button forwardButton = lookup("#↑").query();
    clickOn(forwardButton);
    sleep(600, TimeUnit.MILLISECONDS);
    assertEquals(centerY, myTurtleView.getLayoutY(), 0.01);
    assertEquals(centerX - 50, myTurtleView.getLayoutX(), 0.01);
  }

  @Test
  void testTurtleMoveButtonsRight() {
    Button rightButton = lookup("#→").query();
    clickOn(rightButton);
    sleep(200, TimeUnit.MILLISECONDS);
    assertEquals(centerY, myTurtleView.getLayoutY(), 0.01);
    assertEquals(centerX, myTurtleView.getLayoutX(), 0.01);

    Button forwardButton = lookup("#↑").query();
    clickOn(forwardButton);
    sleep(600, TimeUnit.MILLISECONDS);
    assertEquals(centerY, myTurtleView.getLayoutY(), 0.01);
    assertEquals(centerX + 50, myTurtleView.getLayoutX(), 0.01);
  }


}
