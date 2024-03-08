package slogo.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;
import javafx.animation.Animation;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Show TestFX driving GUI for testing.
 *
 * @author Robert C. Duvall
 */
public class AnimatedShapeTest extends DukeApplicationTest {

  public static final double MATCH_TOLERANCE = 0.01;

  // keep in case need to call application methods in tests
  private AnimatedShape myApp;
  // keep GUI components used in multiple tests
  private Rectangle myActor;


  // this method is run BEFORE EACH test to set up application in a fresh state
  @Override
  public void start(Stage stage) {
    // create app and add scene for testing to given stage
    myApp = new AnimatedShape();
    Scene scene = myApp.makeScene(1000, 600);
    stage.setScene(scene);
    stage.show();

  }

  @Test
  void testA() {
    assertEquals(1, 1);
  }


  @Test
  void testAnimation() {

    Animation animation = myApp.makeAnimation(myActor, 350, 50, 90);
    animation.play();
    sleep(4, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
    Animation test = myApp.makeAnimation(myActor, 0, 0, 0);
    animation.play();
    sleep(4, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
  }

  @Test
  void testSetResources() {
  }

  @Test
  void testResourceNumbers() {

  }

  @Test
  void testResourceColors() {

  }
}
