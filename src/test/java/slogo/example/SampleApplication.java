package slogo.example;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SampleApplication extends Application {

  private static final double MATCH_TOLERANCE = 0.01;

  // keep in case need to call application methods in tests
  private AnimatedShape myApp;
  // keep GUI components used in multiple tests
  private Rectangle myActor;

  // this method is run BEFORE EACH test to set up application in a fresh state
  @Override
  public void start(Stage stage) {
    // create app and add scene for testing to given stage
    myApp = new AnimatedShape();
    Scene scene = myApp.makeScene(400, 100);
    stage.setScene(scene);
    stage.show();

    // components that will be reused in different tests
    // Hardcoded values for the Rectangle size and color
    myActor = new Rectangle(50, 50, Color.BLUE);
    Animation animation = myApp.makeAnimation(myActor, 350, 50, 90);
    animation.play();

  }
}
