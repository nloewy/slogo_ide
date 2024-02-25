package slogo.view.pages;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import slogo.Controller;
import slogo.model.api.TurtleRecord;
import slogo.view.ButtonUtil;
import slogo.view.FrontEndTurtle;
import slogo.view.Scene;
import slogo.view.View;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ResourceBundle;

/*
The View will already know the XMLFile data when
this is initialized. This will periodically
call View for updates and to schedule
animation keyframes.
 */

public class MainScreen implements Scene {

    private final Controller controller;
    private javafx.scene.Scene scene;
    private VBox layout = new VBox(10);
//    private Controller controller;
    private View view;
    public static final String DEFAULT_RESOURCE_PACKAGE = "slogo.example.languages.";
    private ResourceBundle myResources;
    Group root;
    Stage stage;
    TextField field;
    Button testButton;
    Map<FrontEndTurtle, Double[]> myTurtlePositions = new HashMap<>();
    private static final double FRAME_RATE = 4.0;
    private final Timeline animation = new Timeline();
    private final double speed = 0.75;
    Button submitField;

  // Add an XMLFile object to this when Model adds one
  // Controller calls this with an XML File
    public MainScreen(View view, Stage stage, Controller controller) throws FileNotFoundException {
        super();
        this.stage = stage;
        this.view = view;
        this.controller = controller;

        initResources();
        initScene();

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames()
            .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)), e -> update()));
    }

  @Override
  public void initScene() {
    field = new TextField();


    submitField = ButtonUtil.generateButton(myResources.getString("Submit"), 251, 100, event -> {
      view.setCommandString(field.getText());
      field.clear();
    });

    testButton = ButtonUtil.generateButton("test", 300, 100, event -> {
      view.onUpdateTurtleState(new TurtleRecord(0, 100, 100, true, true, 90));
    });

    HBox hbox = new HBox();
    hbox.setSpacing(10);
    hbox.getChildren().addAll(field, submitField, testButton);
    root = new Group();
    hbox.setAlignment(javafx.geometry.Pos.CENTER);
    hbox.setLayoutX(100);
    hbox.setLayoutY(100);
    root.getChildren().add(hbox);

    for (FrontEndTurtle turtle : view.getTurtles()) {
      myTurtlePositions.put(turtle, turtle.getPosition());
      root.getChildren().add(turtle.getDisplay());
    }

    System.out.println("Testing");
  }

  private void initResources() {
    // Initialize resource bundle based on the current language from the controller
    String currentLanguage = controller.getCurrentLanguage();
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + currentLanguage);
  }

  public void update() {
    System.out.println(view.getTurtles());
    Map<FrontEndTurtle, Double[]> deltaPositions = new HashMap<>();

    for (FrontEndTurtle turtle : view.getTurtles()) {

      // this is here for testing, this should be replaced with animation
      turtle.getDisplay().setLayoutX(turtle.getPosition()[0]);
      turtle.getDisplay().setLayoutY(turtle.getPosition()[0]);

      // if (!myTurtlePositions.containsKey(turtle)) {
      // System.out
      // myTurtlePositions.put(turtle, turtle.getPosition());
      // }

      // deltaPositions.put(
      // turtle,
      // new Double[] {
      // turtle.getPosition()[0] - myTurtlePositions.get(turtle)[0],
      // turtle.getPosition()[1] - myTurtlePositions.get(turtle)[1]
      // });
    }
    // handleTurtleAnimation(deltaPositions);
    // syncTurtlesWithView();
  }

  public void handleTurtleAnimation(Map<FrontEndTurtle, Double[]> deltas) {
    animation.stop();

    Timeline localAnimation = new Timeline();
    localAnimation.setCycleCount(5);
    localAnimation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)), e -> animateMovement(deltas)));

    animation.play();
  }

  public void animateMovement(Map<FrontEndTurtle, Double[]> deltas) {
    for (FrontEndTurtle turtle : deltas.keySet()) {
      double xStep = deltas.get(turtle)[0] / 5;
      double yStep = deltas.get(turtle)[1] / 5;

      turtle.getDisplay().setLayoutX(turtle.getPosition()[0] + xStep);
      turtle.getDisplay().setLayoutY(turtle.getPosition()[1] + yStep);
    }
  }

  public void syncTurtlesWithView() {
    for (FrontEndTurtle turtle : view.getTurtles()) {
      myTurtlePositions.remove(turtle);
      myTurtlePositions.put(turtle, turtle.getPosition());
    }
  }

  @Override
  public void setUp() {
    field.setLayoutX(100);
    field.setLayoutY(100);
    animation.play();
  }

  @Override
  public javafx.scene.Scene getScene() {
    return scene;
  }

  @Override
  public Group getGroup() {
    return root;
  }

}

