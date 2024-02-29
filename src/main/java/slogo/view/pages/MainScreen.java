
package slogo.view.pages;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.Controller;
import slogo.view.ButtonUtil;
import slogo.view.FrontEndTurtle;
import slogo.view.View;
import slogo.view.ViewInternal;

/*
The View will already know the XMLFile data when
this is initialized. This will periodically
call View for updates and to schedule
animation keyframes.
 */

public class MainScreen implements ViewInternal {

  private final Controller controller;
  private javafx.scene.Scene scene;
  private BorderPane layout;
  private ScrollPane variablesPane;
  private VBox variablesBox;
  private HBox commandBox;
  // private Controller controller;
  private View view;
  public static final String DEFAULT_RESOURCE_PACKAGE = "slogo.example.languages.";
  private ResourceBundle myResources;
  Group root;
  Stage stage;
  TextField field;
  Map<FrontEndTurtle, Double[]> myTurtlePositions = new HashMap<>();
  private static final double FRAME_RATE = 4.0;
  private final Timeline animation = new Timeline();
  private final double speed = 0.75;
  Button submitField;

  //TEST
  Pane testpane = new Pane();

  // Add an XMLFile object to this when Model adds one
  // Controller calls this with an XML File
  public MainScreen(View view, Stage stage, Controller controller) throws FileNotFoundException {
    super();
    this.stage = stage;

    this.view = view;
    this.controller = controller;

    initResources();

    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)), e -> update()));
  }

  public void initializeTurtleDisplays() {
    for (FrontEndTurtle turtle : view.getTurtles()) {
      // root.getChildren().add(turtle.getDisplay());

      //TEST
      testpane.getChildren().add(turtle.getDisplay());
    }
  }

  public void sendCommandStringToView() {
    view.pushCommand(field.getText());
    field.clear();
  }

  private void initResources() {
    // Initialize resource bundle based on the current language from the controller
    String currentLanguage = controller.getCurrentLanguage();
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + currentLanguage);
  }

  public void update() {
    for (FrontEndTurtle turtle : view.getTurtles()) {
      Line line = new Line(
          turtle.getDisplay().getLayoutX(),
          turtle.getDisplay().getLayoutY(),
          turtle.getPosition()[0],
          turtle.getPosition()[1]);
      line.setStroke(turtle.getPenColor());

      // TODO rotation needs troubleshooting
      turtle.getDisplay().setRotate(turtle.getHeading());

      if (turtle.isPenDisplayed()) {
        root.getChildren().add(line);
      }

      updateVariables();
    }
  }

  private void updateVariables() {
    variablesBox.getChildren().clear();

    for (String key : view.getVariables().keySet()) {
      variablesBox.getChildren().add(new Label(key + view.getVariables().get(key)));
    }
  }

  // public void handleTurtleAnimation(Map<FrontEndTurtle, Double[]> deltas) {
  // animation.stop();

  // Timeline localAnimation = new Timeline();
  // localAnimation.setCycleCount(5);
  // localAnimation.getKeyFrames()
  // .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)), e ->
  // animateMovement(deltas)));

  // animation.play();
  // }

  // public void animateMovement(Map<FrontEndTurtle, Double[]> deltas) {
  // for (FrontEndTurtle turtle : deltas.keySet()) {
  // double xStep = deltas.get(turtle)[0] / 5;
  // double yStep = deltas.get(turtle)[1] / 5;

  // turtle.getDisplay().setLayoutX(turtle.getPosition()[0] + xStep);
  // turtle.getDisplay().setLayoutY(turtle.getPosition()[1] + yStep);
  // }
  // }

  // public void syncTurtlesWithView() {
  // for (FrontEndTurtle turtle : view.getTurtles()) {
  // myTurtlePositions.remove(turtle);
  // myTurtlePositions.put(turtle, turtle.getPosition());
  // }
  // }

  @Override
  public void setUp() {

    layout = new BorderPane();

    Button testButton = ButtonUtil.generateButton("test", 0, 0, (event) -> {
      view.onUpdateValue("" + new Random().nextDouble(), new Random().nextDouble());
    });

    variablesBox = new VBox();
    variablesBox.setSpacing(10);
    variablesBox.setAlignment(javafx.geometry.Pos.CENTER);

    variablesPane = new ScrollPane(variablesBox);
    variablesPane.setPrefSize(200, 200);
    // variablesPane.setLayoutX(100);
    // variablesPane.setLayoutY(100);

    field = new TextField();
    field.setPrefWidth(1000);
    field.setPrefHeight(100);
    field.setAlignment(Pos.TOP_LEFT);

    submitField = ButtonUtil.generateButton(myResources.getString("Submit"), 251, 100, event -> {
      sendCommandStringToView();
    });

    commandBox = new HBox();
    commandBox.setSpacing(10);
    commandBox.getChildren().addAll(field, submitField);
    commandBox.setAlignment(javafx.geometry.Pos.CENTER);

    root = new Group();
    // root.getChildren().add(testButton);
    // root.getChildren().add(variablesPane);
    // root.getChildren().add(commandBox);

    initializeTurtleDisplays();

    layout.setPrefSize(1400,650);
    layout.setCenter(testpane);
    layout.setBottom(commandBox);
    layout.setRight(variablesPane);
    root.getChildren().add(layout);

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
