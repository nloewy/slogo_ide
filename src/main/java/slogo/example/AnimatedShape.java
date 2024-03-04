package slogo.example;

import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * An example class to show how to animate objects over time.
 * <p>
 * Disclaimer: The code here for the GUI is poorly written (everything is done in this class), and
 * is merely to demonstrate sequencing animations and the power possible using properties.
 * <p>
 * NOTE: some methods are package friendly to allow for testing more directly
 *
 * @author Robert C. Duvall
 */
public class AnimatedShape extends Application {

  public static final String TITLE = "JavaFX Animation Example";
  public static final String CONFIGURATION_RESOURCE_PATH =
      AnimatedShape.class.getPackageName() + ".";

  private Node myActor;
  private ResourceBundle myResources;


  public AnimatedShape() {
    super();
  }

  /**
   * Start the program, give complete control to JavaFX.
   * <p>
   * Default version of main() is actually included within JavaFX, so this is not technically
   * necessary!
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * @see Application#start(Stage)
   */
  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle(TITLE);
    primaryStage.setScene(
        makeScene(1000, 600));
    primaryStage.show();

    // create and start animation, could be used in separate contexts
    Animation animation = makeAnimation(myActor, 1000,
        300,
        90);
    animation.play();
  }

  // create a simple scene
  Scene makeScene(int width, int height) {
    Group root = new Group();
    myActor = makeActor(0, 0,
        50, 50,
        Color.BLACK);
    root.getChildren().add(myActor);
    return new Scene(root, width, height);
  }

  // create something to animate
  Node makeActor(int x, int y, int width, int height, Paint color) {
//        Shape result = new Rectangle(x, y, width, height);
    Shape result = new Rectangle(width, height);
    result.setLayoutX(x);
    result.setLayoutY(y);
    result.setFill(color);
    result.setId("actor");
    return result;
  }

  // create sequence of animations
  Animation makeAnimation(Node agent, int endX, int endY, int rotateDegrees) {
    // create something to follow
    agent = myActor;
    Path path = new Path();
    path.getElements().addAll(
        new MoveTo(agent.getBoundsInParent().getMinX(), agent.getBoundsInParent().getMinY()),
        new LineTo(endX, endY));
    // create an animation where the shape follows a path
    PathTransition pt = new PathTransition(Duration.seconds(2), path, agent);
    // create an animation that rotates the shape
    RotateTransition rt = new RotateTransition(Duration.seconds(1));
    rt.setByAngle(rotateDegrees);
    // put them together in order
    return new SequentialTransition(agent, pt, rt);
  }
}
