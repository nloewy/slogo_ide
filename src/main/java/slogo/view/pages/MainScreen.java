
package slogo.view.pages;

import java.io.FileNotFoundException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.Controller;
import slogo.view.UserInterfaceUtil;
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
  private static final double WINDOW_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
  private static final double WINDOW_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
  private final Controller controller;
  private Scene scene;
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
  Pane root;
  Stage stage;
  TextField field;
  Text variablesBoxLabel = new Text();
  Text commandHistoryLabel = new Text();
  Text userDefinedCommandsLabel = new Text();
  private static final double FRAME_RATE = 4.0;
  private final double speed = 0.75;
  Button submitField;
  Button play;
  Button pause;
  Pane centerPane = new Pane();

  // Add an XMLFile object to this when Model adds one
  // Controller calls this with an XML File
  public MainScreen(View view, Stage stage, Controller controller) throws FileNotFoundException {
    this.stage = stage;

    this.view = view;
    this.controller = controller;

    initResources();
  }

  public void initializeTurtleDisplays() {
    for (FrontEndTurtle turtle : view.getTurtles()) {
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

  }

  public void updateVariables() {
    variablesBox.getChildren().clear();
    variablesBox.getChildren().add(variablesBoxLabel);

    for (String key : view.getVariableValues().keySet()) {
      List<String> relatedCommands = view.getVariableCommands().get(key);
      Button openRelatedCommands = new Button(key + " :: " + view.getVariableValues().get(key));
      openRelatedCommands.setId("variable");

      openRelatedCommands.setOnAction((event) -> {
        String commands = "";
        for (String command : relatedCommands) {
          commands = commands + "\n" + command;
        }
        new Alert(AlertType.CONFIRMATION, "RELATED COMMANDS\n" + commands).show();
      });

      variablesBox.getChildren().add(openRelatedCommands);
    }
  }

  public void updateCommands() {
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
    layout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

    variablesBox = new VBox();
    variablesBox.getStyleClass().add("variable-box");
    variablesBox.setPrefSize(200, WINDOW_HEIGHT - 200);
    variablesPane = new ScrollPane(variablesBox);
    variablesBox.getChildren().add(variablesBoxLabel);

    commandHistoryBox = new HBox();
    commandHistoryBox.getStyleClass().add("history-box");
    commandHistoryBox.setPrefSize(WINDOW_WIDTH, 100);
    commandsHistory = new ScrollPane(commandHistoryBox);
    commandHistoryBox.getChildren().add(commandHistoryLabel);

    userDefinedCommandsBox = new VBox();
    userDefinedCommandsBox.getStyleClass().add("command-box");
    userDefinedCommandsBox.setPrefSize(200, WINDOW_HEIGHT - 200);
    userDefinedCommandsPane = new ScrollPane(userDefinedCommandsBox);
    userDefinedCommandsBox.getChildren().add(userDefinedCommandsLabel);


    textInputBox = new HBox();
    textInputBox.getStyleClass().add("input-box");
    textInputBox.setMaxSize(WINDOW_WIDTH, 100);

    field = new TextField();
    field.setPrefSize(WINDOW_WIDTH - 400, 100);

    submitField = UserInterfaceUtil.generateButton("Submit", event -> {
      sendCommandStringToView();
    });

    controller.addLanguageObserver((s) -> {
      ResourceBundle newLang = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + s);
      submitField.setText(newLang.getString("Submit"));
      variablesBoxLabel.setText(newLang.getString("varBox"));
      commandHistoryLabel.setText(newLang.getString("histBox"));
      userDefinedCommandsLabel.setText(newLang.getString("commandBox"));
    });

    /**
    play = ButtonUtil.generateButton("Play", 300, 300, (event) -> {
      for (FrontEndTurtle t : view.getTurtles()) {
        if (t.getAnimation() != null) {
          t.getAnimation().play();
        }
      }
    });

    pause = ButtonUtil.generateButton("Pause", 300, 400, (event) -> {
      for (FrontEndTurtle t : view.getTurtles()) {
        if (t.getAnimation() != null) {
          t.getAnimation().pause();
        }
      }
    });

     */
    textInputBox.getChildren().addAll(field, submitField);//, play, pause);
    root = new Pane();

    initializeTurtleDisplays();


//    centerPane.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, null)));
    layout.setCenter(centerPane);
    layout.setBottom(textInputBox);
    layout.setRight(variablesPane);
    layout.setTop(commandsHistory);
    layout.setLeft(userDefinedCommandsPane);
    root.getChildren().add(layout);
  }

  @Override
  public javafx.scene.Scene getScene() {
    return scene;
  }

  @Override
  public Group getGroup() {
    return null;
  }
  public Region getRegion() {
    return root;
  }

  public void addLine(Line line) {
    root.getChildren().add(line);
  }
}
