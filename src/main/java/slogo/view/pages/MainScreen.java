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

/*
The View will already know the XMLFile data when
this is initialized. This will periodically
call View for updates and to schedule
animation keyframes.
 */

public class MainScreen implements  SlogoListener {

  public static final String DEFAULT_RESOURCE_PACKAGE = "slogo.example.languages.";
  private static final double WINDOW_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
  private static final double WINDOW_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
  private static final double FRAME_RATE = 4.0;
  private final Controller controller;
  private final Timeline timeline = new Timeline();
  private final double speed = 1;
  private Group root;
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
  private double centerX;
  private double centerY;

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

    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)), e -> playAnimation()));
    paused = false;
    initResources();
    timeline.play();
    speedSlider = new Slider();
    setUp();
    scene = new Scene(root, width, height);
    controller.updateCurrentTheme(scene);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();
    centerX =  centerPane.getBoundsInParent().getWidth() / 2;
    centerY =  centerPane.getBoundsInParent().getHeight() / 2;
    turtles.add(new FrontEndTurtle(1, centerX, centerY, Color.BLUE, true, 0, defaultImage));

    initializeTurtleDisplays();
  }

  public void addParser(Consumer<String> parseMethod) throws FileNotFoundException {
    parse = parseMethod;
  }

  private void finishCurrAnimation() {
    if (currAnimation != null) {
      currAnimation.play();
      paused = true;
    }
  }

  private void playSingleAnimation() {
    if (!myAnimation.isEmpty()) {
      Animation animation = myAnimation.poll();
      animation.setOnFinished(event -> {
        animationPlaying = false;
        playAnimation(); // Continue playing other animations after finishing this one
      });
      animationPlaying = true;
      animation.play();
    }
  }

  private void playAnimation() {
    if (!animationPlaying && !myAnimation.isEmpty() && !paused) {
      currAnimation = myAnimation.poll();
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

  private void initializeTurtleDisplays() {
    for (FrontEndTurtle turtle : turtles) {
      centerPane.getChildren().add(turtle.getDisplay());
    }
  }

  private void sendCommandStringToView() {
    pushCommand(field.getText());
    field.clear();
  }

  private void initResources() {
    String currentLanguage = controller.getCurrentLanguage();
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + currentLanguage);
  }

  private void updateVariables() {
    variablesBox.getChildren().clear();
    variablesBox.getChildren().add(variablesBoxLabel);

    for (String key : variableValues.keySet()) {
      List<String> relatedCommands = variableCommands.get(key);
      Button openRelatedCommands = new Button(key + " :: " + variableValues.get(key));
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

  private void updateCommands() {
    updateCommandBox(commandHistoryBox, commandHistoryLabel, commandHistory );
    updateCommandBox(userDefinedCommandsBox, userDefinedCommandsLabel, userDefinedCommandHistory);
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

  private void setSpeedSliderHandler(ChangeListener<Number> speedSliderHandler) {
    speedSlider.valueProperty().addListener(speedSliderHandler);
  }

  private void setUp() {
    layout = new BorderPane();
    layout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

    createVariablesBox();
    createCommandHistoryBox();
    createUserDefinedCommandBox();
    createTextInputBox();
    createSpeedSlider();

    field = new TextField();
    field.setPrefSize(WINDOW_WIDTH - 700, 300);

    submitField = UserInterfaceUtil.generateButton("Submit", event -> {
      sendCommandStringToView();
      paused = false;
    });

    play = UserInterfaceUtil.generateButton("Play", event -> {paused = false;});
    pause = UserInterfaceUtil.generateButton("Pause", event -> {paused = true;});

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
      turtles.forEach(turtle -> turtle.setPenColor(Color.valueOf(event)));
    });
    colorDropDown.getOnAction().handle(new ActionEvent());

    ComboBox<ComboChoice> backgroundDropDown = UserInterfaceUtil.generateComboBox(penColors, 100, 300, (s) -> s, (event) -> {
      centerPane.setStyle("-fx-background-color: " + event);
    });
    backgroundDropDown.setValue(null);
    dropdowns.getChildren().addAll(colorDropDown, backgroundDropDown);
    List<Region> mainButtons = List.of(submitField, play, pause, step, dropdowns);
    mainButtons.forEach(b -> b.getStyleClass().add("main-screen-button"));
    addLanguageObserver(colorDropDown, backgroundDropDown);


    textInputBox.getChildren().addAll(speedSlider, field);
    textInputBox.getChildren().addAll(mainButtons);
    layout.setCenter(centerPane);
    layout.setBottom(textInputBox);
    layout.setRight(new VBox(variablesPane, userDefinedCommandsPane)); // Stack variablesPane and userDefinedCommandsPane vertically
    layout.setLeft(commandsHistory);
    root = new Group();
    root.getChildren().add(layout);
  }

  private void addLanguageObserver(ComboBox<ComboChoice> colorDropDown,
      ComboBox<ComboChoice> backgroundDropDown) {
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
  }

  private void createSpeedSlider() {
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
  }

  private void createTextInputBox() {
    textInputBox = new HBox();
    textInputBox.getStyleClass().add("input-box");
    textInputBox.setMaxSize(WINDOW_WIDTH, 200);
  }

  private void createUserDefinedCommandBox() {
    userDefinedCommandsBox = new VBox();
    userDefinedCommandsBox.getStyleClass().add("command-box");
    userDefinedCommandsBox.setPrefSize(400, WINDOW_HEIGHT / 2);
    userDefinedCommandsPane = new ScrollPane(userDefinedCommandsBox);
    userDefinedCommandsBox.getChildren().add(userDefinedCommandsLabel);
  }

  private void createCommandHistoryBox() {
    commandHistoryBox = new VBox();
    commandHistoryBox.getStyleClass().add("history-box");
    commandHistoryBox.setPrefSize(400, WINDOW_HEIGHT / 2);
    commandsHistory = new ScrollPane(commandHistoryBox);
    commandHistoryBox.getChildren().add(commandHistoryLabel);
  }

  private void createVariablesBox() {
    variablesBox = new VBox();
    variablesBox.getStyleClass().add("variable-box");
    variablesBox.setPrefSize(400, WINDOW_HEIGHT - 400);
    variablesPane = new ScrollPane(variablesBox);
    variablesBox.getChildren().add(variablesBoxLabel);
  }

  public void setTurtleImage(File f) {
    try {
      defaultImage = new Image(new FileInputStream(f));
      for (FrontEndTurtle t : turtles) {
        t.setImage(defaultImage);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void pushCommand(String s) {
    commandString = s;
    parse.accept(commandString);
    updateCommands();
  }

  @Override
  public void onUpdateValue(String variableName, Number newValue) {
    variableCommands.put(variableName, List.of(commandHistory.peek()));
    variableValues.put(variableName, newValue);
    updateVariables();
  }

  private void setPosition(FrontEndTurtle turtle, double x, double y, double newHeading) {
    double oldX = turtle.getX();
    double oldY = turtle.getY();
    double oldHeading = turtle.getHeading();
    double distance = Math.sqrt(Math.pow((y - oldY), 2) + Math.pow((x - oldX), 2));
    double rotation = Math.abs(newHeading - oldHeading);
    double duration = (distance + rotation) / mySpeed;
    int numSteps = (int) (duration / 0.005);
    Timeline animation = new Timeline();
    animation.setCycleCount(1);
    for (int i = 1; i <= numSteps; i++) {
      double progress = (double) i / numSteps;
      double offsetx = turtle.getDisplay().getImage().getWidth()/4;
      double offsety = turtle.getDisplay().getImage().getHeight()/4;
      Line line = turtle.drawLine(oldX+offsetx, oldY+offsety, oldX + (x - oldX) * progress+offsetx, oldY + (y - oldY) * progress+offsety);
      KeyFrame keyFrame = new KeyFrame(Duration.seconds(duration * progress),
          e -> {
            turtle.getDisplay().setLayoutX(oldX + (x - oldX) * progress);
            turtle.getDisplay().setLayoutY(oldY + (y - oldY) * progress);
            turtle.getDisplay().setRotate(oldHeading + (newHeading - oldHeading) * progress);
            if (!centerPane.getChildren().contains(line)) {
              centerPane.getChildren().add(line);
            }
          });
      animation.getKeyFrames().add(keyFrame);
    }
    turtle.setPosition(x, y, newHeading);
    myAnimation.add(animation);
  }

  @Override
  public void onUpdateTurtleState(TurtleRecord turtleState) {
    for (FrontEndTurtle turtle : turtles) {
      if (turtle.getId() == turtleState.id()) {
        turtle.setIsPenDisplayed(turtleState.pen());
        setPosition(turtle, turtleState.x() + centerX, turtleState.y() + centerY,
            turtleState.heading());
        return;
      }
    }
    turtles.add(new FrontEndTurtle(
        turtleState.id(), turtleState.x() + centerX, turtleState.y() + centerY,
        Color.BLACK, true, turtleState.heading(), defaultImage));
  }

  @Override
  public void onResetTurtle(int id) {
    for (FrontEndTurtle turtle : turtles) {
      if (turtle.getId() == id) {
        turtle.setIsPenDisplayed(false);
        turtle.setPosition(centerX, centerY, 0);
        turtle.setImage(defaultImage);
      }
    }
  }

  //val returned by last command
  //add it to history next to the command
  @Override
  public void onReturn(double value, String string) {
    commandHistory.add(string);
    //print val perhaps ext to command?
    updateCommands();
  }

  @Override
  public void onUserDefinedCommand(String string) {
    userDefinedCommandHistory.add(string);
    updateCommands();
  }
}

