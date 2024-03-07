package slogo.view.pages;

import static slogo.view.UserInterfaceUtil.generateButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import slogo.Controller;
import slogo.model.api.SlogoListener;
import slogo.model.api.TurtleRecord;
import slogo.view.ComboChoice;
import slogo.view.FrontEndTurtle;
import slogo.view.UserInterfaceUtil;

public class MainScreen implements SlogoListener {

  public static final String DEFAULT_RESOURCE_PACKAGE = "slogo.example.languages.";
  private static final double WINDOW_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
  private static final double WINDOW_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
  private static final double FRAME_RATE = 4.0;
  private static final int height = 600;
  private static final int width = 1000;
  public static final Double[] ORIGIN = new Double[]{width / 2.0, height / 2.0};
  private static final double PIXELS_PER_SECOND = 25;
  private final Controller controller;
  private Button reset;
  private final Timeline timeline = new Timeline();
  private final double speed = 1;
  private final Slider speedSlider;
  ComboBox<ComboChoice> colorDropDown;
  private Group root;
  private final Stage stage;
  private TextField field;
  private final Text variablesBoxLabel = new Text();
  private final Text commandHistoryLabel = new Text();
  private final Text userDefinedCommandsLabel = new Text();
  private Button submitField;
  private Button play;
  private Button pause;
  private Button step;
  private TextField distanceField;
  private TextField rotateField;
  private String commandHistoryText;
  private Button uploadTurtle;
  private Button openNewWindow;
  private Button help;
  private Button save;
  private Button upload;
  private final Pane centerPane = new Pane();
  private final Scene scene;
  private GridPane paletteGrid;
  private boolean paused;
  private BorderPane layout;
  private ScrollPane variablesPane;
  private ScrollPane commandsHistory;
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
  private final Map<String, Number> variableValues;
  private final List<FrontEndTurtle> turtles;
  private final Stack<String> commandHistory;
  private final Stack<String> userDefinedCommandHistory;
  private Image defaultImage;
  private final Queue<Animation> myAnimation;
  private String commandString;
  private final String lang;
  private Consumer<String> parse;
  private final double centerX;
  private final double centerY;
  private Map<Integer, List<Integer>> palette;

  // Add an XMLFile object to this when Model adds one
  // Controller calls this with an XML File
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
    centerX = centerPane.getBoundsInParent().getWidth() / 2;
    centerY = centerPane.getBoundsInParent().getHeight() / 2;
    turtles.add(new FrontEndTurtle(1, centerX, centerY, Color.BLUE, true, 0, defaultImage, this));

    initializeTurtleDisplays();
  }

  public void addParser(Consumer<String> parseMethod, String slogoContent)
      throws FileNotFoundException {
    parse = parseMethod;

    if (slogoContent != null) {
      pushCommand(slogoContent);
    }

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

        makeInputDialog(variableValues.get(key).toString(), "Enter New Value",
            "Enter a new value for " + key, "New value:\n\nRELATED COMMANDS\n" + commands,
            true,
            newValue -> {
              try {
                variableValues.put(key, Double.valueOf(newValue));
                pushCommand("MAKE " + key + " " + newValue);

                new Alert(AlertType.INFORMATION, "New value for " + key + " saved: " + newValue)
                    .showAndWait();

              } catch (Exception e) {
                new Alert(AlertType.INFORMATION, "Value Must be a Number");
              }
            });
      });

    }

  }

  private void makeInputDialog(String value, String title, String header, String content, Boolean needsInput, Consumer<String> consumer) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.getEditor().setText(value);
    dialog.getEditor().setDisable(!needsInput);
    dialog.setTitle(title);
    dialog.setHeaderText(header);
    dialog.setContentText(content);
    dialog.showAndWait().ifPresent(consumer);
  }




  private void updateCommandBox(VBox box, Text label, List<String> history) {
    box.getChildren().clear();
    box.getChildren().add(label);
    for (String s : history) {
      String[] lines = s.split("\n");
      VBox vbox = new VBox();
      TitledPane titledPane = new TitledPane();
      titledPane.setText(lines[0]);
      titledPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue) {
          String fullText = String.join("\n",
              Arrays.copyOfRange(lines, 1, lines.length)); // Join lines excluding the
          // first one
          titledPane.setContent(new Label(fullText)); // Set the full command content when expanded
          titledPane.setText(lines[0]); // Display the first line when expanded
        } else {
          titledPane.setContent(null); // Remove content when collapsed
          titledPane.setText(lines[0]);
        }
      });
      titledPane.setExpanded(false); // Start collapsed
      Button openCustomCommand = new Button(myResources.getString("Execute"));
      openCustomCommand.setId("customCommandPrompt");
      String[] commandParts = lines[0].split("\\s+");
      List<String> parameters = new ArrayList<>();
      for (String part : commandParts) {
        if (part.startsWith(":")) {
          parameters.add(part);
        }
      }
      vbox.getChildren().addAll(titledPane, openCustomCommand);
      box.getChildren().add(vbox);
      if(commandParts.length < 2) {
        openCustomCommand.setOnAction(event -> {
          makeInputDialog("", "Execute This Command",
              "", "", false,
              newValue -> {
                pushCommand(s);
              });
        });
        continue;
      }
      String commandName = commandParts[1];
      Boolean hasParameters = !parameters.isEmpty();
      String commandHeaderText =
          hasParameters ? "Enter values for parameters: " + String.join(", ", parameters) : "";
      openCustomCommand.setOnAction(event -> {
        makeInputDialog("", "Execute This Command",
            commandHeaderText, "", hasParameters,
            newValue -> {
              pushCommand(commandName + " " + newValue);
            });
      });

    }
  }


  private void fullReset() throws IOException {
    controller.openNewIDESession("");
    stage.close();
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
    field.setPromptText(myResources.getString("EnterCommand"));
    field.setPrefSize(WINDOW_WIDTH - 1200, 300);

    field.setOnAction(event -> {
      if (!field.getText().isEmpty()) {
        sendCommandStringToView();
      }
    });

    submitField = UserInterfaceUtil.generateButton("Submit", event -> {
      sendCommandStringToView();
      paused = false;
    });

    openNewWindow = UserInterfaceUtil.generateButton("Open New Window", event -> {
      try {
        controller.openNewIDESession(null);
        paused = false;
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    uploadTurtle = generateButton("UploadTurtle", (event) -> {
      handleLoadTurtleImage();
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

    help = UserInterfaceUtil.generateButton("Help", event -> {
      showHelpPopup();
    });

    upload = UserInterfaceUtil.generateButton("Upload", event -> {
      controller.loadSession("add");
      String newSlogoContent = controller.getSlogoContent();
      pushCommand(newSlogoContent);
    });

    save = UserInterfaceUtil.generateButton("Save", event -> saveToFile());

    reset = UserInterfaceUtil.generateButton("Reset", event -> {
      try {
        fullReset();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });


    VBox dropdowns = new VBox();
    dropdowns.getStyleClass().add("main-dropdowns");
    ResourceBundle defaultResources = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + "English");
    ObservableList<ComboChoice> penColors = FXCollections.observableArrayList();
    for (String color : defaultResources.getString("PenColors").split(",")) {
      penColors.add(new ComboChoice(color, color));
    }

    colorDropDown = UserInterfaceUtil.generateComboBox(penColors, "colorBox",100, 300, (s) -> s, (event) -> {
      turtles.forEach(turtle -> turtle.setPenColor(Color.valueOf(event)));
    });
    colorDropDown.getOnAction().handle(new ActionEvent());

    ComboBox<ComboChoice> backgroundDropDown = UserInterfaceUtil.generateComboBox(penColors, "backgroundBox",100,
        300, (s) -> s,
        (event) -> {
          centerPane.setStyle("-fx-background-color: " + event);
        });
    backgroundDropDown.setValue(null);
    dropdowns.getChildren().addAll(colorDropDown, backgroundDropDown);
    List<Region> mainButtons = List.of(submitField, play, pause, step, help, reset, upload, uploadTurtle,
        save, dropdowns, openNewWindow);
    mainButtons.forEach(b -> b.getStyleClass().add("main-screen-button"));
    addLanguageObserver(colorDropDown, backgroundDropDown);
    distanceField = new TextField("50");
    distanceField.setPrefWidth(60);
    rotateField = new TextField("90");
    rotateField.setPrefWidth(60);
    HBox turtleMoveBox = new HBox(distanceField, rotateField);
    turtleMoveBox.setTranslateY(50);
    turtleMoveBox.setPadding(new javafx.geometry.Insets(0, 0, 0, -100));
    Button leftButton = UserInterfaceUtil.generateButton("←", event -> {
      pushCommand(myResources.getString("Left").split("\\|")[0] + " " + rotateField.getText());
    });
    Button rightButton = UserInterfaceUtil.generateButton("→", event -> {
      pushCommand(myResources.getString("Right").split("\\|")[0] + " " + rotateField.getText());
    });
    Button forwardButton = UserInterfaceUtil.generateButton("↑", event -> {
      pushCommand(myResources.getString("Forward").split("\\|")[0] + " " + distanceField.getText());
    });
    Button backwardButton = UserInterfaceUtil.generateButton("↓", event -> {
      pushCommand(myResources.getString("Backward").split("\\|")[0] + " " + distanceField.getText());
    });

    VBox upDownButtons = new VBox(forwardButton, backwardButton);
    upDownButtons.setTranslateY(-15);
    upDownButtons.setSpacing(10);
    HBox turtleButtons = new HBox(leftButton, upDownButtons, rightButton);
    turtleButtons.setPadding(new javafx.geometry.Insets(50, 0, 0, 0));
    paletteGrid = new GridPane();
    textInputBox.getChildren().addAll(new VBox(speedSlider, paletteGrid), turtleMoveBox, turtleButtons, field);
    textInputBox.getChildren().addAll(mainButtons);
    layout.setCenter(centerPane);
    layout.setBottom(textInputBox);
    layout.setRight(new VBox(variablesPane, userDefinedCommandsPane)); // Stack variablesPane and
    // userDefinedCommandsPane vertically
    layout.setLeft(commandsHistory);
    root = new Group();
    root.getChildren().add(layout);
  }


  private void updatePalettePane() {
    // Clear existing content from the palette grid
    paletteGrid.getChildren().clear();

    int col = 0;
    int row = 0;
    for (Map.Entry<Integer, List<Integer>> entry : palette.entrySet()) {
      int colorKey = entry.getKey();
      List<Integer> rgbValues = entry.getValue();
      Rectangle colorBox = new Rectangle(10, 10,
          Color.rgb(rgbValues.get(0), rgbValues.get(1), rgbValues.get(2)));
      paletteGrid.add(new HBox(new Label(Integer.toString(colorKey)), colorBox), col, row);
      row++;
      if (row == 8) {
        col++;
        row = 0;
      }
    }
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

  private void saveToFile() {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Save Options");
    alert.setHeaderText("Choose what to save:");
    ButtonType buttonTypeOne = new ButtonType("Save Preferences Only");
    ButtonType buttonTypeTwo = new ButtonType("Save Commands Only");
    ButtonType closeButtonType = new ButtonType("Close");
    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
    alert.getButtonTypes().add(closeButtonType);
    alert.showAndWait().ifPresent(response -> {
      if (response == buttonTypeOne) {
        savePreferences();
      } else if (response == buttonTypeTwo) {
        saveCommandsToFile();
      } else if (response == closeButtonType) {
        alert.close();
      }
    });
  }

  private void savePreferences() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
    File file = fileChooser.showSaveDialog(stage);
    if (file != null) {
      try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
        writer.println("<preferences>");
        writer.println("  <language>" + controller.getCurrentLanguage() + "</language>");
//        writer.println("  <theme>" + controller.getCurrentTheme() + "</theme>");
        writer.println("  <theme>" + "IDK TRYING TO GET" + "</theme>");
        writer.println("  <penColor>" + getPenColor() + "</penColor>");
        writer.println("</preferences>");

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void saveCommandsToFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(
        new File("data/examples/savedSlogos"));
    fileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("SLogo Files", "*.slogo"));
    File file = fileChooser.showSaveDialog(stage);
    if (file != null) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        for (String command : commandHistory) {
          writer.write(command);
          writer.newLine();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  private String getPenColor() {
    return "PEN COLOR";
  }

  public void setPenColor(String color) {
    for (ComboChoice choice : colorDropDown.getItems()) {
      if ((String.valueOf(choice)).equals(color)) {
        colorDropDown.setValue(choice);
        break;
      }
    }
  }

  private void showHelpPopup() {
    // Popup popup = new Popup();

    Stage popup = new Stage();

    popup.initStyle(StageStyle.UTILITY);
    popup.initModality(Modality.APPLICATION_MODAL);
    VBox content = new VBox(10);
    content.setStyle("-fx-background-color: white; -fx-padding: 10;");

    Button closeButton = new Button(myResources.getString("Close"));
    closeButton.setOnAction(e -> popup.hide());


    String languagedCommands = myResources.getString("commands");

    Map<String, Map<String, String>> commandDetails = controller.getCommandDetailsFromXML(
        languagedCommands);
    for (String command : commandDetails.keySet()) {
      Hyperlink commandLink = new Hyperlink(command);
      commandLink.setOnAction(e -> showCommandDetailsPopup(command, commandDetails.get(command)));
      content.getChildren().add(commandLink);
    }

    content.getChildren().add(closeButton);
    // popup.getContent().add(content);

    helpPopupModality(popup, content);
    // popup.show(root.getScene().getWindow());
  }

  private void showCommandDetailsPopup(String command, Map<String, String> details) {
    Stage popup = new Stage();
    popup.initStyle(StageStyle.UTILITY);
    popup.initModality(Modality.APPLICATION_MODAL);

    VBox content = new VBox(10);
    content.setStyle("-fx-background-color: white; -fx-padding: 10;");

    Label commandLabel = new Label(myResources.getString("Command") + ": " + command);
    Label descriptionLabel = new Label(
        myResources.getString("Description") + ": " + details.get("description"));
    Label exampleLabel = new Label(
        myResources.getString("Example") + ": " + details.get("example"));
    HBox parametersBox = new HBox(10);
    Label parametersLabel = new Label(
        myResources.getString("Parameters") + ": " + details.get("parameters"));
    TextField parametersField = new TextField();
    parametersBox.getChildren().addAll(parametersLabel, parametersField);
    Label returnValueLabel = new Label(
        myResources.getString("ReturnValue") + ": " + details.get("returnValue"));

    Button closeButton = new Button(myResources.getString("Close"));
    closeButton.setOnAction(e -> popup.hide());

    Button executeButton = new Button(myResources.getString("Execute"));
    executeButton.setOnAction(e -> {
      pushCommand(command + " " + parametersField.getText());
      field.clear();
    });

    content.getChildren().addAll(commandLabel, descriptionLabel, exampleLabel, parametersBox,
        returnValueLabel, closeButton, executeButton);
    // popup.getContent().add(content);

    helpPopupModality(popup, content);
  }

  private void helpPopupModality(Stage popup, VBox content) {
    final Delta dragDelta = new Delta();
    content.setOnMousePressed(mouseEvent -> {
      dragDelta.x = popup.getX() - mouseEvent.getScreenX();
      dragDelta.y = popup.getY() - mouseEvent.getScreenY();
    });
    content.setOnMouseDragged(mouseEvent -> {
      popup.setX(mouseEvent.getScreenX() + dragDelta.x);
      popup.setY(mouseEvent.getScreenY() + dragDelta.y);
    });
    Scene scene = new Scene(content);
    popup.setScene(scene);
    popup.show();
  }

  private void addLanguageObserver(ComboBox<ComboChoice> colorDropDown,
      ComboBox<ComboChoice> backgroundDropDown) {
    controller.addLanguageObserver((s) -> {
      ResourceBundle newLang = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + s);
      submitField.setText(newLang.getString("Submit"));
      variablesBoxLabel.setText(newLang.getString("varBox"));
      commandHistoryLabel.setText(newLang.getString("histBox"));
      commandHistoryText = newLang.getString("histBox");
      userDefinedCommandsLabel.setText(newLang.getString("commandBox"));
      play.setText(newLang.getString("Play"));
      pause.setText(newLang.getString("Pause"));
      openNewWindow.setText(newLang.getString("OpenNew"));
      uploadTurtle.setText(newLang.getString("UploadTurtle"));

      String[] newPenColors = newLang.getString("PenColors").split(",");
      ObservableList<ComboChoice> colorItems = colorDropDown.getItems();
      ObservableList<ComboChoice> backgroundItems = backgroundDropDown.getItems();
      for (int i = 0; i < newPenColors.length; i++) {
        colorItems.set(i, new ComboChoice(newPenColors[i], colorItems.get(i).getValue()));
        backgroundItems.set(i, new ComboChoice(newPenColors[i], backgroundItems.get(i).getValue()));
        if (colorItems.get(i).getValue().equals(colorDropDown.getValue().toString())) {
          colorDropDown.setValue(colorItems.get(i));
        }
      }
      step.setText(newLang.getString("Step"));

      help.setText(newLang.getString("Help"));
      save.setText(newLang.getString("Save"));
      upload.setText(newLang.getString("Upload"));
      distanceField.setPromptText(newLang.getString("DistanceField"));
      rotateField.setPromptText(newLang.getString("RotateField"));


    });
  }

  private void createSpeedSlider() {
    speedSlider.setMin(10);
    speedSlider.setMax(500);
    speedSlider.setValue(mySpeed);
    speedSlider.setShowTickLabels(true);
    speedSlider.setShowTickMarks(true);
    speedSlider.setMajorTickUnit(10);
    setSpeedSliderHandler((observable, oldValue, newValue) -> {
      mySpeed = newValue.intValue();
      if (mySpeed == speedSlider.getMax()) {
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
      for (FrontEndTurtle t : turtles) {
        defaultImage = new Image(new FileInputStream(f), defaultImage.getWidth(),
            defaultImage.getHeight(), true, true);
        t.setImage(defaultImage);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void pushCommand(String s) {
    commandString = s;
    parse.accept(commandString);
  }

  @Override
  public void onUpdateValue(String variableName, Number newValue) {
    variableValues.put(variableName, newValue);
    updateVariables();
  }



  private void setPosition(FrontEndTurtle turtle, double x, double y, double newHeading, boolean visible) {
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
      Line line = turtle.drawLine(oldX + offsetx, oldY + offsety, oldX + (x - oldX) * progress + offsetx,
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

  @Override
  public void onUpdateTurtleState(TurtleRecord turtleState) {
    for (FrontEndTurtle turtle : turtles) {
      System.out.println(turtles);
      if (turtle.getId() == turtleState.id()) {
        turtle.setIsPenDisplayed(turtleState.pen());
        setPosition(turtle, turtleState.x() + centerX, turtleState.y() + centerY,
            turtleState.heading(), turtleState.visible());
        List<Integer> newPenColor = palette.get(turtleState.penColorIndex());
        if (newPenColor != null) {
          turtle.setPenColor(new Color(newPenColor.get(0)/255, newPenColor.get(1)/255, newPenColor.get(2)/255, 1));
        }

        List<Integer> newBackgroundColor = palette.get(turtleState.bgIndex());
        if (newBackgroundColor != null) {
          centerPane.setStyle("-fx-background-color: rgb(" + newBackgroundColor.get(0) + "," + newBackgroundColor.get(1) + "," + newBackgroundColor.get(2) + ")");
        }

        return;
      }
    }
    FrontEndTurtle newTurtle = new FrontEndTurtle(turtleState.id(), turtleState.x() + centerX, turtleState.y() + centerY,
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
    commandHistoryLabel.setText(commandHistoryText + String.format(" %.2f",value));
    updateCommandBox(commandHistoryBox, commandHistoryLabel, commandHistory);
  }

  @Override
  public void onUserDefinedCommand(String string) {
    userDefinedCommandHistory.add(string);
    updateCommandBox(userDefinedCommandsBox, userDefinedCommandsLabel, userDefinedCommandHistory);

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
    updatePalettePane();
  }

  class Delta {

    double x, y;
  }
}

