
package slogo.view.pages;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
  private ScrollPane commandsHistory;
  private ScrollPane userDefinedCommandsPane;
  private VBox variablesBox;
  private HBox textInputBox;
  private HBox commandHistoryBox;
  private VBox userDefinedCommandsBox;
  private View view;
  public static final String DEFAULT_RESOURCE_PACKAGE = "slogo.example.languages.";
  private ResourceBundle myResources;
  Group root;
  Stage stage;
  TextField field;
  Text variablesBoxLabel = new Text("Variables");
  Text commandHistoryLabel = new Text("History");
  Text userDefinedCommandsLabel = new Text("My Commands");
  Map<FrontEndTurtle, Double[]> myTurtlePositions = new HashMap<>();
  private static final double FRAME_RATE = 4.0;
  private final Timeline animation = new Timeline();
  private final double speed = 0.75;
  Button submitField;
  Pane centerPane = new Pane();

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

      // TEST
      centerPane.getChildren().add(turtle.getDisplay());
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

      if (
        turtle.isPenDisplayed() && 
        !root.getChildren().contains(turtle.getLastPath()
        )) {
        root.getChildren().add(turtle.getLastPath());
      }
    }
    updateVariables();
    updateCommands();
  }

  private void updateVariables() {
    variablesBox.getChildren().clear();
    variablesBox.getChildren().add(variablesBoxLabel);

    for (String key : view.getVariables().keySet()) {
      variablesBox.getChildren().add(new Label(key + view.getVariables().get(key)));
    }
  }

  private void updateCommands() {
    commandHistoryBox.getChildren().clear();
    commandHistoryBox.getChildren().add(commandHistoryLabel);
    for (String s : view.getCommandHistory()) {
      commandHistoryBox.getChildren().add(new Label(s));
    }

    userDefinedCommandsBox.getChildren().clear();
    userDefinedCommandsBox.getChildren().add(userDefinedCommandsLabel);
    for (String s : view.getUserDefinedCommandHistory()) {
      userDefinedCommandsBox.getChildren().add(new Label(s));
    }
  }

  @Override
  public void setUp() {

    layout = new BorderPane();

    variablesBox = new VBox();
    variablesBox.setSpacing(10);
    variablesBox.setAlignment(javafx.geometry.Pos.CENTER);
    variablesPane = new ScrollPane(variablesBox);
    variablesPane.setPrefSize(200, 200);
    variablesBox.getChildren().add(variablesBoxLabel);

    commandHistoryBox = new HBox();
    commandHistoryBox.setSpacing(10);
    commandHistoryBox.setAlignment(javafx.geometry.Pos.CENTER);
    commandsHistory = new ScrollPane(commandHistoryBox);
    commandsHistory.setPrefSize(1000, 100);
    commandHistoryBox.getChildren().add(commandHistoryLabel);

    userDefinedCommandsBox = new VBox();
    userDefinedCommandsBox.setSpacing(10);
    userDefinedCommandsBox.setAlignment(javafx.geometry.Pos.CENTER);
    userDefinedCommandsPane = new ScrollPane(userDefinedCommandsBox);
    userDefinedCommandsPane.setPrefSize(200, 200);
    userDefinedCommandsBox.getChildren().add(userDefinedCommandsLabel);

    field = new TextField();
    field.setPrefWidth(1000);
    field.setPrefHeight(100);
    field.setAlignment(Pos.TOP_LEFT);

    variablesBoxLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
    commandHistoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
    userDefinedCommandsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

    submitField = ButtonUtil.generateButton(myResources.getString("Submit"), 251, 100, event -> {
      sendCommandStringToView();
    });

    textInputBox = new HBox();
    textInputBox.setSpacing(10);
    textInputBox.getChildren().addAll(field, submitField);
    textInputBox.setAlignment(javafx.geometry.Pos.CENTER);

    root = new Group();
    // root.getChildren().add(variablesPane);
    // root.getChildren().add(commandBox);

    initializeTurtleDisplays();

    layout.setPrefSize(1400, 650);

    layout.setCenter(centerPane);
    layout.setBottom(textInputBox);
    layout.setRight(variablesPane);
    layout.setTop(commandsHistory);
    layout.setLeft(userDefinedCommandsPane);
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
