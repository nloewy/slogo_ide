

package slogo.view.pages;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.Controller;
import slogo.model.api.TurtleRecord;
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
    private VBox variablesBox;

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
      view.onUpdateValue(field.getText(), field.getLength());
      sendCommandStringToView();
      //test for adding variables
    });

    //I'm not using the pen boolean i dont know what it is
    testButton = ButtonUtil.generateButton("test", 300, 100, event -> {
      view.onUpdateTurtleState(new TurtleRecord(0, 100, 100, true, true, 90));
      view.onUpdateTurtleState(new TurtleRecord(1, 300, 200, true, true, 90));
    });

    Button testButtonB = ButtonUtil.generateButton("testB", 300, 100, event -> {
      view.onUpdateTurtleState(new TurtleRecord(0, 200, 500, true, true, 90));
      view.onUpdateTurtleState(new TurtleRecord(1, 700, 800, true, true, 90));
    });

    Button testButtonC = ButtonUtil.generateButton("testC", 300, 100, event -> {
      view.onUpdateTurtleState(new TurtleRecord(0, 0, 0, true, true, 90));
      view.onUpdateTurtleState(new TurtleRecord(1, 200, 0, true, true, 90));
    });

    HBox hbox = new HBox();
    hbox.setSpacing(10);
    hbox.getChildren().addAll(field, submitField, testButton, testButtonB, testButtonC);
    root = new Group();
    hbox.setAlignment(javafx.geometry.Pos.CENTER);
    hbox.setLayoutX(100);
    hbox.setLayoutY(100);
    root.getChildren().add(hbox);

    //TODO Add variables from XML file here maybe
    variablesBox = new VBox();
    variablesBox.setAlignment(javafx.geometry.Pos.CENTER);
    variablesBox.setLayoutX(300);
    variablesBox.setLayoutY(500);
    variablesBox.setId("vbox");

    root.getChildren().add(variablesBox);

    initializeTurtleDisplays();

    System.out.println("Testing");
  }

  public void initializeTurtleDisplays() {
    for (FrontEndTurtle turtle : view.getTurtles()) {
      // myTurtlePositions.put(turtle, turtle.getPosition());
      root.getChildren().add(turtle.getDisplay());
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
    // Map<FrontEndTurtle, Double[]> deltaPositions = new HashMap<>();

    for (FrontEndTurtle turtle : view.getTurtles()) {
        Line line = new Line(
                turtle.getDisplay().getLayoutX(),
                turtle.getDisplay().getLayoutY(),
                turtle.getPosition()[0],
                turtle.getPosition()[1]);
        line.setStroke(turtle.getPenColor());

        // this is here for testing, this should be replaced with animation
        turtle.getDisplay().setLayoutX(turtle.getPosition()[0]);
        turtle.getDisplay().setLayoutY(turtle.getPosition()[1]);
        //TODO rotation needs troubleshooting
        turtle.getDisplay().setRotate(turtle.getHeading());

        if (turtle.isPenDisplayed()) {
            root.getChildren().add(line);
        }

        updateVariables();

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

  private void updateVariables() {
    variablesBox.getChildren().clear();
    
    for (String key : view.getVariables().keySet()) {
      variablesBox.getChildren().add(new Label(key + view.getVariables().get(key)));
    }
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

