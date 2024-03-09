package slogo.view.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.model.api.SlogoListener;
import slogo.model.api.TurtleRecord;
import slogo.view.ComboChoice;
import slogo.view.Controller;
import slogo.view.FrontEndTurtle;
import slogo.view.UserInterfaceUtil;
import slogo.view.pages.components.HistoryBox;
import slogo.view.pages.components.InputBox;

/**
 * The MainScreen class is responsible for setting up and displaying the main user interface
 * of the SLogo application. It initializes the application window, sets up the user interface
 * components such as the command input box, variable display, and turtle graphics pane, and
 * manages the application's animation timeline. The class implements the SlogoListener interface
 * to respond to updates from the SLogo model, such as turtle movements, variable changes, and
 * user-defined commands.
 */
public class MainScreen implements SlogoListener {

  public static final String DEFAULT_RESOURCE_PACKAGE = "slogo.languages.";
  private static final double WINDOW_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
  private static final double WINDOW_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
  private static final double FRAME_RATE = 4.0;
  private static final int height = 600;
  private static final int width = 1000;
  private final Controller controller;
  private final Timeline timeline = new Timeline();
  private final double speed = 1;
  private final Stage stage;
  private final Text variablesBoxLabel = new Text();
  private final Pane centerPane = new Pane();
  private final Scene scene;
  private final Map<String, Number> variableValues;
  private final List<FrontEndTurtle> turtles;
  private final Stack<String> commandHistory;
  private final Stack<String> userDefinedCommandHistory;
  private final Queue<Animation> myAnimation;
  private final double centerX;
  private final double centerY;
  private ComboBox<ComboChoice> colorDropDown;
  private Group root;
  private TextField field;
  private Text commandHistoryLabel;
  private Text userDefinedCommandsLabel;
  private String commandHistoryText;
  private GridPane paletteGrid;
  private BorderPane layout;
  private ScrollPane variablesPane;
  private ScrollPane commandsHistory;
  private HistoryBox historyBox;
  private HistoryBox definedCommandBox;
  private InputBox inputBox;
  private ScrollPane userDefinedCommandsPane;
  private VBox variablesBox;
  private String recentlyUpdatedVariable;
  private String recentlyUpdatedValue;
  private HBox textInputBox;
  private VBox commandHistoryBox;
  private VBox userDefinedCommandsBox;
  private double mySpeed;
  private ResourceBundle myResources;
  private boolean animationPlaying;
  private Duration pausedTime;
  private Animation currAnimation;
  private List<Integer> oldPenColor;
  private List<Integer> oldBackgroundColor;
  private Image defaultImage;
  private String commandString;
  private String lang;
  private Consumer<String> parse;
  private Map<Integer, List<Integer>> palette;

  /**
   * Constructs a MainScreen object with the specified stage and controller. Initializes the
   * user interface components, sets up the application scene, and starts the animation timeline.
   *
   * @param stage The primary stage of the application.
   * @param controller The controller managing interactions between the model and view.
   * @throws FileNotFoundException If the default turtle image file is not found.
   */
  public MainScreen(Stage stage, Controller controller) throws FileNotFoundException {
    this.stage = stage;
    this.controller = controller;
    palette = new HashMap<>();
    mySpeed = 250;
    animationPlaying = false;
    myAnimation = new ArrayDeque<>();
    lang = "EG";
    commandString = "";
    variableValues = new LinkedHashMap<>();
    turtles = new ArrayList<>();
    commandHistory = new Stack<String>();
    userDefinedCommandHistory = new Stack<String>();
    try {
      defaultImage = new Image(
          new FileInputStream("src/main/resources/turtleimages/DefaultTurtle.png"));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)), e -> playAnimation()));
    initResources();
    timeline.play();
    setUp();
    scene = new Scene(root, width, height);
    controller.updateCurrentTheme(scene);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();
    centerX = centerPane.getBoundsInParent().getWidth() / 2;
    centerY = centerPane.getBoundsInParent().getHeight() / 2;
    centerPane.setId("CenterPane");
    turtles.add(new FrontEndTurtle(1, centerX, centerY, Color.BLUE, true, 0,
        defaultImage, this));
    initializeTurtleDisplays();
  }

  /**
   * Adds a parser method and processes initial SLogo content, if provided. This method
   * allows for the dynamic parsing of SLogo commands.
   *
   * @param parseMethod The parsing method to be used for interpreting SLogo commands.
   * @param slogoContent Initial content in SLogo language to be parsed, can be null.
   * @throws FileNotFoundException If there is an issue accessing any required files during parsing.
   */
  public void addParser(Consumer<String> parseMethod, String slogoContent)
      throws FileNotFoundException {
    parse = parseMethod;

    if (slogoContent != null) {
      pushCommand(slogoContent);
    }

  }

  /**
   * Updates the display of variables in the variable box. This method refreshes the list
   * of variables and their values, allowing for interactive updates and modifications by the user.
   */
  public void updateVariables() {
    variablesBox.getChildren().clear();
    variablesBox.getChildren().add(variablesBoxLabel);

    for (String key : variableValues.keySet()) {
      Button openRelatedCommands = new Button(key + " :: " + variableValues.get(key));
      openRelatedCommands.setId("variable");
      variablesBox.getChildren().add(openRelatedCommands);
      openRelatedCommands.setOnAction(event -> {
        String commands = "";
        for (String command : commandHistory) {
          if (command.contains(key)) {
            commands += "\n" + command;
          }
        }

        UserInterfaceUtil.makeInputDialog(variableValues.get(key).toString(),"Enter New Value",
            "Enter a new value for " + key,
            "New value:\n\nRELATED COMMANDS\n" + commands, true,
            newValue -> {
              try {
                variableValues.put(key, Double.valueOf(newValue));
                pushCommand("MAKE " + key + " " + newValue);

                new Alert(AlertType.INFORMATION,
                    "New value for " + key + " saved: " + newValue).showAndWait();

              } catch (Exception e) {
                new Alert(AlertType.INFORMATION, "Value Must be a Number");
              }
            });
      });

    }

  }

  /**
   * Processes and executes a given SLogo command. This method facilitates the execution of
   * commands input by the user or triggered through other interactions within the application
   * and starts the parsing process.
   *
   * @param s The SLogo command string to be executed.
   */
  public void pushCommand(String s) {
    commandString = s;
    parse.accept(commandString);
  }

  public void setTurtleImage(File f) {
    try {
      for (FrontEndTurtle t : turtles) {
        defaultImage = new Image(new FileInputStream(f), defaultImage.getWidth(),
            defaultImage.getHeight(), true, true);
        t.setImage(defaultImage);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onUpdateValue(String variableName, Number newValue) {
    variableValues.put(variableName, newValue);
    updateVariables();
  }

  @Override
  public void onUpdateTurtleState(TurtleRecord turtleState) {
    for (FrontEndTurtle turtle : turtles) {

      if (turtle.getId() == turtleState.id()) {
        turtle.setIsPenDisplayed(turtleState.pen());
        setPosition(turtle, turtleState.x() + centerX, turtleState.y() + centerY,
            turtleState.heading(), turtleState.visible());
        List<Integer> newPenColor = palette.get(turtleState.penColorIndex());
        if (newPenColor != null) {
          turtle.setPenColor(new Color(
              newPenColor.get(0) / 255, newPenColor.get(1) / 255,
              newPenColor.get(2) / 255, 1));
        }

        List<Integer> newBackgroundColor = palette.get(turtleState.bgIndex());

        System.out.println(palette);
        if (newBackgroundColor != null) {

          centerPane.setStyle("-fx-background-color: rgb(" + newBackgroundColor.get(0) + ","
              + newBackgroundColor.get(1) + "," + newBackgroundColor.get(2) + ")");
        }

        return;
      }
    }
    FrontEndTurtle newTurtle = new FrontEndTurtle(turtleState.id(), turtleState.x() + centerX,
        turtleState.y() + centerY,
        Color.BLACK, true, turtleState.heading(), defaultImage, this);
    turtles.add(newTurtle);
    centerPane.getChildren().add(newTurtle.getDisplay());
  }


  @Override
  public void onResetTurtle(int id) {
    for (FrontEndTurtle turtle : turtles) {
      if (turtle.getId() == id) {
        turtle.setPosition(centerX, centerY, 0, true);
        turtle.setImage(defaultImage);
        for (Line line : turtle.getPathHistory()) {
          centerPane.getChildren().remove(line);
        }
      }
    }
  }

  // val returned by last command
  // add it to history next to the command
  @Override
  public void onReturn(double value, String string) {
    commandHistory.add(string);
    commandHistoryLabel.setText(commandHistoryText + String.format(" %.2f", value));
    historyBox.updateCommandBox(commandHistory, this::pushCommand);
  }

  @Override
  public void onUserDefinedCommand(String string) {
    userDefinedCommandHistory.add(string);
    definedCommandBox.updateUserDefinedCommandBox(userDefinedCommandHistory, this::pushCommand);
  }

  @Override
  public void onSetActiveTurtles(List<Integer> ids) {
    for (FrontEndTurtle turtle : turtles) {
      turtle.setIsActive(ids.contains(turtle.getId()));
    }
  }

  @Override
  public void onUpdatePalette(Map<Integer, List<Integer>> palette) {
    this.palette = palette;
    inputBox.updatePalettePane(palette);
  }

  /**
   * Sets up the layout of the main screen, and adds all features to the root.
   */
  private void setUp() {
    layout = new BorderPane();
    layout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

    createVariablesBox();
    createCommandHistoryBox();
    createUserDefinedCommandBox();
    createLanguageObserver();
    createTextInputBox();
    setLayout();

    root = new Group();
    root.getChildren().add(layout);
  }

  private void createLanguageObserver() {
    controller.addLanguageObserver((s) -> {
      ResourceBundle newLang = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + s);
      variablesBoxLabel.setText(newLang.getString("varBox"));
      commandHistoryLabel.setText(newLang.getString("histBox"));
      commandHistoryText = newLang.getString("histBox");
      userDefinedCommandsLabel.setText(newLang.getString("commandBox"));
      lang = s;
    });
  }

  private void setLayout() {
    layout.setCenter(centerPane);
    layout.setBottom(textInputBox);
    layout.setRight(new VBox(variablesPane, userDefinedCommandsPane)); // Stack variablesPane and
    layout.setLeft(commandsHistory);
  }

  private void setLabelTextColor(Label label, List<Integer> rgbValues) {
    // Calculate luminance of the color to determine if text should be dark or light
    double luminance =
        (0.299 * rgbValues.get(0) + 0.587 * rgbValues.get(1) + 0.114 * rgbValues.get(2)) / 255;

    // Set text color based on luminance
    if (luminance > 0.5) {
      label.setTextFill(Color.BLACK);
    } else {
      label.setTextFill(Color.WHITE);
    }
  }

  private void handleLoadTurtleImage() {
    File dataFile = slogo.view.pages.Screen.IMAGE_CHOOSER.showOpenDialog(stage);
    setTurtleImage(dataFile);
  }

  public void setPenColor(String color) {
    for (ComboChoice choice : colorDropDown.getItems()) {
      if ((String.valueOf(choice)).equals(color)) {
        colorDropDown.setValue(choice);
        break;
      }
    }
  }

  private void createTextInputBox() {
    inputBox = new InputBox(WINDOW_WIDTH, WINDOW_HEIGHT, myResources);
    inputBox.setFieldAction(this::sendCommandStringToView);
    textInputBox = inputBox.getBox();
    field = inputBox.getField();
    paletteGrid = inputBox.getPaletteGrid();
    inputBox.createSpeedSlider(mySpeed, speed -> mySpeed = speed);

    inputBox.setValues(controller, currAnimation, commandHistory, stage, centerPane);
    inputBox.setUpDropdowns(turtles);
    inputBox.setUpButtons(this::sendCommandStringToView, this::handleLoadTurtleImage,
        this::playSingleAnimation, this::finishCurrAnimation, this::pushCommand);
    inputBox.setUpTurtleMovement(this::pushCommand);

    inputBox.addOtherComponentsToBox();

    inputBox.addButtonsToBox();
  }


  private void createUserDefinedCommandBox() {
    definedCommandBox = new HistoryBox(WINDOW_WIDTH, WINDOW_HEIGHT, myResources);
    definedCommandBox.setStyleClass("command-box");
    userDefinedCommandsBox = definedCommandBox.getBox();
    userDefinedCommandsPane = definedCommandBox.getPane();
    userDefinedCommandsLabel = definedCommandBox.getLabel();
  }

  private void createCommandHistoryBox() {
    historyBox = new HistoryBox(WINDOW_WIDTH, WINDOW_HEIGHT, myResources);
    historyBox.setStyleClass("history-box");
    commandHistoryBox = historyBox.getBox();
    commandHistoryLabel = historyBox.getLabel();
    commandsHistory = historyBox.getPane();
  }

  private void createVariablesBox() {
    variablesBox = new VBox();
    variablesBox.getStyleClass().add("variable-box");
    variablesBox.setPrefSize(400, WINDOW_HEIGHT - 400);
    variablesPane = new ScrollPane(variablesBox);
    variablesBox.getChildren().add(variablesBoxLabel);
  }


  private void finishCurrAnimation() {
    if (currAnimation != null) {
      currAnimation.play();
      inputBox.setPaused(true);
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
    if (!animationPlaying && !myAnimation.isEmpty() && !inputBox.isPaused()) {
      currAnimation = myAnimation.poll();
      currAnimation.setOnFinished(event -> {
        animationPlaying = false;
        currAnimation = null;
        playAnimation();
      });
      animationPlaying = true;
      currAnimation.play();
    } else if (animationPlaying && inputBox.isPaused()) {
      if (currAnimation != null) {
        pausedTime = currAnimation.getCurrentTime();
        currAnimation.pause();
      }
    } else if (animationPlaying && pausedTime != null) {
      // If animation was paused and now unpaused, resume from the paused time
      if (currAnimation != null) {
        currAnimation.playFrom(pausedTime);
        pausedTime = null;
      }
    }
  }

  private void setPosition(FrontEndTurtle turtle, double x, double y, double newHeading,
      boolean visible) {
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
      double offsetx = turtle.getDisplay().getImage().getWidth() / 4;
      double offsety = turtle.getDisplay().getImage().getHeight() / 4;
      Line line = turtle.drawLine(oldX + offsetx, oldY + offsety,
          oldX + (x - oldX) * progress + offsetx,
          oldY + (y - oldY) * progress + offsety);
      KeyFrame keyFrame = new KeyFrame(Duration.seconds(duration * progress),
          e -> {
            turtle.getDisplay().setLayoutX(oldX + (x - oldX) * progress);
            turtle.getDisplay().setLayoutY(oldY + (y - oldY) * progress);
            turtle.getDisplay().setRotate(oldHeading + (newHeading - oldHeading) * progress);
            if (!centerPane.getChildren().contains(line)) {
              centerPane.getChildren().add(line);
            }
            turtle.getDisplay().setVisible(visible);

          });
      animation.getKeyFrames().add(keyFrame);
    }
    animation.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), e -> {
      turtle.setPosition(x, y, newHeading, visible);
    }));
    turtle.setPosition(x, y, newHeading, visible);
    myAnimation.add(animation);
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

}

