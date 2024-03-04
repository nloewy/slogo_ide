package slogo.view.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.image.Image;
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
import slogo.model.api.SlogoListener;
import slogo.model.api.TurtleRecord;
import slogo.view.ComboChoice;
import slogo.view.FrontEndTurtle;
import slogo.view.UserInterfaceUtil;

import slogo.view.ViewInternal;

/*
The View will already know the XMLFile data when
this is initialized. This will periodically
call View for updates and to schedule
animation keyframes.
 */

public class MainScreen implements ViewInternal, SlogoListener {

  public static final String DEFAULT_RESOURCE_PACKAGE = "slogo.example.languages.";
  private static final double WINDOW_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
  private static final double WINDOW_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
  private static final double FRAME_RATE = 4.0;
  private final Controller controller;
  private final Timeline timeline = new Timeline();
  private final double speed = 1;
  private Pane root;
  private Stage stage;
  private TextField field;
  private Text variablesBoxLabel = new Text();
  private Text commandHistoryLabel = new Text();
  private Text userDefinedCommandsLabel = new Text();
  private Button submitField;
  private Button play;
  private Button pause;
  private Button step;
  private Pane centerPane = new Pane();
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
  private ResourceBundle myResources;
  private boolean animationPlaying;
  private final Slider speedSlider;
  private Duration pausedTime;
  private Animation currAnimation;

  private static final int height = 600;
  private static final int width = 1000;
  public static final Double[] ORIGIN = new Double[]{width / 2.0, height / 2.0};
  private static final double PIXELS_PER_SECOND = 25;
  private Map<String, List<String>> variableCommands;
  private Map<String, Number> variableValues;
  private List<FrontEndTurtle> turtles;
  private Stack<String> commandHistory;
  private Stack<String> userDefinedCommandHistory;
  private Image defaultImage;
  private Queue<Animation> myAnimation;
  private String commandString;
  private String lang;
  private Consumer<String> parse;

  // Add an XMLFile object to this when Model adds one
  // Controller calls this with an XML File
  public MainScreen(Stage stage, Controller controller) throws FileNotFoundException {
    this.stage = stage;
    this.controller = controller;
    mySpeed = 50;
    animationPlaying = false;
    myAnimation = new ArrayDeque<>();
    lang = "EG";
    commandString = "";
    variableCommands = new LinkedHashMap<>();
    variableValues = new LinkedHashMap<>();
    turtles = new ArrayList<>();
    commandHistory = new Stack<String>();
    userDefinedCommandHistory = new Stack<String>();

    try {
      defaultImage = new Image(new FileInputStream("src/main/resources/DefaultTurtle.png"));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    turtles.add(new FrontEndTurtle(1, ORIGIN, Color.BLUE, true, 0, defaultImage));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)), e -> playAnimation()));
    paused = false;
    initResources();
    timeline.play();
    speedSlider = new Slider();
  }

  public void run(Consumer<String> parseMethod) throws FileNotFoundException {
    parse = parseMethod;
    setUp();
    scene = new Scene(getRegion(), width, height);
    controller.updateCurrentTheme(scene);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();
  }

  private void finishCurrAnimation() {
    if (currAnimation != null) {
      currAnimation.play();
      paused = true;
    }
  }

  private void playSingleAnimation() {
    if (!getAnimation().isEmpty()) {
      Animation animation = getAnimation().poll();
      animation.setOnFinished(event -> {
        animationPlaying = false;
        playAnimation(); // Continue playing other animations after finishing this one
      });
      animationPlaying = true;
      animation.play();
    }
  }


  private void playAnimation() {
    if (!animationPlaying && !getAnimation().isEmpty() && !paused) {
      currAnimation = getAnimation().poll();
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
    for (FrontEndTurtle turtle : getTurtles()) {
      centerPane.getChildren().add(turtle.getDisplay());
    }
  }

  public void sendCommandStringToView() {
    pushCommand(field.getText());
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

    for (String key : getVariableValues().keySet()) {
      List<String> relatedCommands = getVariableCommands().get(key);
      Button openRelatedCommands = new Button(key + " :: " + getVariableValues().get(key));
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
    updateCommandBox(commandHistoryBox, commandHistoryLabel, getCommandHistory() );
    updateCommandBox(userDefinedCommandsBox, userDefinedCommandsLabel, getUserDefinedCommandHistory());
  }



  private void updateCommandBox(VBox box, Text label, List<String> history) {
    box.getChildren().clear();
    box.getChildren().add(label);
    for (String s : history) {
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
      box.getChildren().add(titledPane);
    }
  }


  public void setSpeedSliderHandler(ChangeListener<Number> speedSliderHandler) {
    speedSlider.valueProperty().addListener(speedSliderHandler);
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

    step = UserInterfaceUtil.generateButton("Step", event -> {
      if (currAnimation == null) {
        playSingleAnimation();
      } else {
        finishCurrAnimation();
      }
    });

    VBox dropdowns = new VBox();
    dropdowns.getStyleClass().add("main-dropdowns");

    ResourceBundle defaultResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
    ObservableList<ComboChoice> penColors = FXCollections.observableArrayList();
    for (String color : defaultResources.getString("PenColors").split(",")) {
      penColors.add(new ComboChoice(color, color));
    }

    ComboBox<ComboChoice> colorDropDown = UserInterfaceUtil.generateComboBox(penColors, 100, 300, (s) -> s, (event) -> {
          getTurtles().forEach(turtle -> turtle.setPenColor(Color.valueOf(event)));
        });
    colorDropDown.getOnAction().handle(new ActionEvent());

    ComboBox<ComboChoice> backgroundDropDown = UserInterfaceUtil.generateComboBox(penColors, 100, 300, (s) -> s, (event) -> {
          centerPane.setStyle("-fx-background-color: " + event);
        });
    backgroundDropDown.setValue(null);

    dropdowns.getChildren().addAll(colorDropDown, backgroundDropDown);

    List<Region> mainButtons = List.of(submitField, play, pause, step, dropdowns);
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
      String[] newPenColors = newLang.getString("PenColors").split(",");
      ObservableList<ComboChoice> colorItems = colorDropDown.getItems();
      ObservableList<ComboChoice> backgroundItems = backgroundDropDown.getItems();
      for (int i = 0; i < newPenColors.length; i++) {
        colorItems.set(i, new ComboChoice(newPenColors[i], colorItems.get(i).getValue()));
        backgroundItems.set(i, new ComboChoice(newPenColors[i], backgroundItems.get(i).getValue()));
        if(colorItems.get(i).getValue().equals(colorDropDown.getValue().toString())) {
          colorDropDown.setValue(colorItems.get(i));
        }
      }
      step.setText(newLang.getString("Step"));
    });

    textInputBox.getChildren().addAll(speedSlider, field);
    textInputBox.getChildren().addAll(mainButtons);


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



  public void setTurtleImage(File f) {
    try {
      defaultImage = new Image(new FileInputStream(f));
      for (FrontEndTurtle t : getTurtles()) {
        t.setImage(defaultImage);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public List<FrontEndTurtle> getTurtles() {
    return turtles;
  }

  public Map<String, List<String>> getVariableCommands() {
    return variableCommands;
  }

  public Map<String, Number> getVariableValues() {
    return variableValues;
  }

  /*
   * Handles the user switching languages.
   * //TODO
   */
  public void setLanguage(String lang) {
    this.lang = lang;
  }

  /*
   * Handles the user switching background color.
   */ //TODO Test

  private boolean hasCommandString() {
    return !commandString.isEmpty();
  }

  /*
   * Returns the current text field body.
   */
  public String getCommandString() throws Exception {
    if (hasCommandString()) {
      String temp = commandString;
      commandString = "";
      commandHistory.push(temp);
      return temp;
    }

    throw new Exception("No Command String Found!");
  }

  public void pushCommand(String s) {
    commandString = s;
    parse.accept(commandString);
    updateCommands();
  }

  public Stack<String> getCommandHistory() {
    return commandHistory;
  }

  public Stack<String> getUserDefinedCommandHistory() {
    return userDefinedCommandHistory;
  }

  @Override
  public void onUpdateValue(String variableName, Number newValue) {
    variableCommands.put(variableName, List.of(commandHistory.peek()));
    variableValues.put(variableName, newValue);
    updateVariables();
  }


  public void setPosition(FrontEndTurtle turtle, double x, double y, double newHeading) {
    double oldX = turtle.getX();
    double oldY = turtle.getY();
    double oldHeading = turtle.getHeading();
    double distance = Math.sqrt(Math.pow((y - oldY), 2) + Math.pow((x - oldX), 2));
    double rotation = Math.abs(newHeading - oldHeading);
    double duration = (distance + rotation) / getSpeed();
    int numSteps = (int) (duration / 0.005);
    Timeline animation = new Timeline();
    animation.setCycleCount(1);
    for (int i = 1; i <= numSteps; i++) {
      double progress = (double) i / numSteps;
      double intermediateX = oldX + (x - oldX) * progress;
      double intermediateY = oldY + (y - oldY) * progress;
      double intermediateHeading = oldHeading + (newHeading - oldHeading) * progress;
      Line line = turtle.drawLine(oldX, oldY, intermediateX, intermediateY);
      KeyFrame keyFrame = new KeyFrame(Duration.seconds(duration * progress),
          e -> {
            turtle.getDisplay().setLayoutX(intermediateX);
            turtle.getDisplay().setLayoutY(intermediateY);
            turtle.getDisplay().setRotate(intermediateHeading);
            addLine(line);
          });
      animation.getKeyFrames().add(keyFrame);
    }
    turtle.setPosition(x, y, newHeading);
    myAnimation.add(animation);
  }


  @Override
  public void onUpdateTurtleState(TurtleRecord turtleState) {
    for (FrontEndTurtle turtle : getTurtles()) {
      if (turtle.getId() == turtleState.id()) {
        turtle.setIsPenDisplayed(turtleState.pen());
        setPosition(turtle, turtleState.x() + ORIGIN[0], turtleState.y() + ORIGIN[1],
            turtleState.heading());
        return;
      }
    }
    turtles.add(new FrontEndTurtle(
        turtleState.id(),
        new Double[]{turtleState.x() + ORIGIN[0], turtleState.y() + ORIGIN[1]},
        Color.BLACK,
        true,
        turtleState.heading(),
        defaultImage));
  }

  @Override
  public void onResetTurtle(int id) {
    for (FrontEndTurtle turtle : turtles) {
      if (turtle.getId() == id) {
        turtle.setIsPenDisplayed(false);
        turtle.setPosition(ORIGIN[0], ORIGIN[1], 0);
        turtle.setImage(defaultImage);
      }
    }
  }

  //val returned by last command
  //add it to history next to the command
  @Override
  public void onReturn(double value, String string) {
    commandHistory.add(string);
    updateCommands();
  }

  @Override
  public void onCommand(String string, boolean userDefined) {

    if (userDefined) {
      userDefinedCommandHistory.add(string);
    }
  }

  public Queue<Animation> getAnimation() {
    return myAnimation;
  }
}
