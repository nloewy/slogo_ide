package slogo.view.pages;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.Controller;
import slogo.view.FrontEndTurtle;
import slogo.view.UserInterfaceUtil;
import slogo.view.View;
import slogo.view.ViewInternal;

/*
The View will already know the XMLFile data when
this is initialized. This will periodically
call View for updates and to schedule
animation keyframes.
 */

public class MainScreen implements ViewInternal {

  public static final String DEFAULT_RESOURCE_PACKAGE = "slogo.example.languages.";
  private static final double WINDOW_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
  private static final double WINDOW_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
  private static final double FRAME_RATE = 4.0;
  private final Controller controller;
  private final Timeline timeline = new Timeline();
  private final double speed = 1;
  Pane root;
  Stage stage;
  TextField field;
  Text variablesBoxLabel = new Text();
  Text commandHistoryLabel = new Text();
  Text userDefinedCommandsLabel = new Text();
  Button submitField;
  Button play;
  Button pause;
  Button step;
  Pane centerPane = new Pane();
  private Scene scene;
  private boolean paused;
  private BorderPane layout;
  private ScrollPane variablesPane;
  private ScrollPane commandsHistory;
  private ScrollPane userDefinedCommandsPane;
  private VBox variablesBox;
  private HBox textInputBox;
  private VBox commandHistoryBox;
  private VBox userDefinedCommandsBox;
  private double mySpeed;
  private final View view;
  private ResourceBundle myResources;
  private boolean animationPlaying;
  private final Slider speedSlider;
  private Duration pausedTime;
  private Animation currAnimation;

  // Add an XMLFile object to this when Model adds one
  // Controller calls this with an XML File
  public MainScreen(View view, Stage stage, Controller controller) throws FileNotFoundException {
    this.stage = stage;
    this.view = view;
    this.controller = controller;
    mySpeed = 50;
    animationPlaying = false;
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)), e -> playAnimation()));
    paused = false;
    initResources();
    timeline.play();
    speedSlider = new Slider();
  }

  private void finishCurrAnimation() {
    if (currAnimation != null) {
      currAnimation.play();
      paused = true;
    }
  }

  private void playSingleAnimation() {
    if (!view.getAnimation().isEmpty()) {
      Animation animation = view.getAnimation().poll();
      animation.setOnFinished(event -> {
        animationPlaying = false;
        playAnimation(); // Continue playing other animations after finishing this one
      });
      animationPlaying = true;
      animation.play();
    }
  }


  private void playAnimation() {
    if (!animationPlaying && !view.getAnimation().isEmpty() && !paused) {
      currAnimation = view.getAnimation().poll();
      currAnimation.setOnFinished(event -> {
        animationPlaying = false;
        currAnimation = null;
        playAnimation();
      });
      animationPlaying = true;
      currAnimation.play();
    } else if (animationPlaying && paused) {
      if (currAnimation != null) {
        pausedTime = currAnimation.getCurrentTime(); // Store the current time when animation is paused
        currAnimation.pause();
      }
    } else if (animationPlaying && !paused && pausedTime != null) {
      // If animation was paused and now unpaused, resume from the paused time
      if (currAnimation != null) {
        currAnimation.playFrom(pausedTime);
        pausedTime = null; // Reset paused time
      }
    }
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
    System.out.println(view.getCommandHistory().get(0));
    commandHistoryBox.getChildren().clear();
    commandHistoryBox.getChildren().add(commandHistoryLabel);

    for (String s : view.getCommandHistory()) {
      String[] lines = s.split("\n");
      TitledPane titledPane = new TitledPane();
      titledPane.setText(lines[0]);
      titledPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue) {
          String fullText = String.join("\n", Arrays.copyOfRange(lines, 1, lines.length)); // Join lines excluding the first one
          titledPane.setContent(new Label(fullText)); // Set the full command content when expanded
          titledPane.setText(lines[0]); // Display the first line when expanded
        } else {
          titledPane.setContent(null); // Remove content when collapsed
          titledPane.setText(lines[0]);
        }
      });
      VBox vbox = new VBox();
      titledPane.setContent(vbox); // Set initial content as empty VBox
      titledPane.setExpanded(false); // Start collapsed
      commandHistoryBox.getChildren().add(titledPane);
    }

    userDefinedCommandsBox.getChildren().clear();
    userDefinedCommandsBox.getChildren().add(userDefinedCommandsLabel);
    for (String s : view.getUserDefinedCommandHistory()) {
      String[] lines = s.split("\n");
      TitledPane titledPane = new TitledPane();
      titledPane.setText(lines[0]);
      titledPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue) {
          String fullText = String.join("\n", Arrays.copyOfRange(lines, 1, lines.length)); // Join lines excluding the first one
          titledPane.setContent(new Label(fullText)); // Set the full command content when expanded
          titledPane.setText(lines[0]); // Display the first line when expanded
        } else {
          titledPane.setContent(null); // Remove content when collapsed
          titledPane.setText(lines[0]);
        }
      });
      VBox vbox = new VBox();
      titledPane.setContent(vbox); // Set initial content as empty VBox
      titledPane.setExpanded(false); // Start collapsed
      userDefinedCommandsBox.getChildren().add(titledPane);
    }

  }


  public void setSpeedSliderHandler(ChangeListener<Number> speedSliderHandler) {
    speedSlider.valueProperty().addListener(speedSliderHandler);
  }

  @Override
  public void setUp() {
    ResourceBundle myResources = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + controller.getCurrentLanguage());

    layout = new BorderPane();
    layout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

    variablesBox = new VBox();
    variablesBox.getStyleClass().add("variable-box");
    variablesBox.setPrefSize(200, WINDOW_HEIGHT - 200);
    variablesPane = new ScrollPane(variablesBox);
    variablesBox.getChildren().add(variablesBoxLabel);

    commandHistoryBox = new VBox();
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
    field.setPrefSize(WINDOW_WIDTH - 700, 200);

    submitField = UserInterfaceUtil.generateButton("Submit", event -> {
      sendCommandStringToView();
      paused = false;
    });

    play = UserInterfaceUtil.generateButton("Play", event -> {
      paused = false;
    });

    pause = UserInterfaceUtil.generateButton("Pause", event -> {
      paused = true;
    });

    ObservableList<String> penColors = FXCollections.observableArrayList(
        myResources.getString("PenColors").split(","));

    ComboBox<String> colorDropDown = UserInterfaceUtil.generateComboBox(penColors, 100, 200, (s) -> s, (event) -> {
          view.getTurtles().forEach(turtle -> turtle.setPenColor(Color.valueOf(event)));
        });

    colorDropDown.getOnAction().handle(new ActionEvent());

    List<Control> mainButtons = List.of(submitField, play, pause, colorDropDown);
    mainButtons.forEach(b -> b.getStyleClass().add("main-screen-button"));

    speedSlider.setMin(10);
    speedSlider.setMax(300);
    speedSlider.setValue(mySpeed);
    speedSlider.setShowTickLabels(true);
    speedSlider.setShowTickMarks(true);
    speedSlider.setMajorTickUnit(10);
    setSpeedSliderHandler((observable, oldValue, newValue) -> {
      mySpeed = newValue.intValue();
      if(mySpeed == speedSlider.getMax()) {
        mySpeed = 100000;
      }
    });

    controller.addLanguageObserver((s) -> {
      ResourceBundle newLang = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + s);
      submitField.setText(newLang.getString("Submit"));
      variablesBoxLabel.setText(newLang.getString("varBox"));
      commandHistoryLabel.setText(newLang.getString("histBox"));
      userDefinedCommandsLabel.setText(newLang.getString("commandBox"));
      play.setText(newLang.getString("Play"));
      pause.setText(newLang.getString("Pause"));
    });

    textInputBox.getChildren().addAll(speedSlider, field);
    textInputBox.getChildren().addAll(mainButtons);

    step = UserInterfaceUtil.generateButton("Step", event -> {
      if (currAnimation == null) {
        playSingleAnimation();
      } else {
        finishCurrAnimation();
      }
    });

    layout.setCenter(centerPane);
    layout.setBottom(textInputBox);
    layout.setRight(variablesPane);
    layout.setTop(commandsHistory);
    layout.setLeft(userDefinedCommandsPane);

    root = new Pane();
    initializeTurtleDisplays();

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
    if (!root.getChildren().contains(line)) {
      root.getChildren().add(line);
    }
  }

  public double getSpeed() {
    return mySpeed;
  }
}
