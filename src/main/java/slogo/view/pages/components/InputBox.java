package slogo.view.pages.components;

import static slogo.view.UserInterfaceUtil.generateButton;
import static slogo.view.pages.MainScreen.DEFAULT_RESOURCE_PACKAGE;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.function.Consumer;
import javafx.animation.Animation;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import slogo.view.ComboChoice;
import slogo.view.Controller;
import slogo.view.FrontEndTurtle;
import slogo.view.UserInterfaceUtil;
import slogo.view.pages.Help;
import slogo.view.pages.Save;

public class InputBox {

  private final HBox textInputBox;
  private final ResourceBundle myResources;
  private final TextField field;
  private Controller controller;
  private Animation currAnimation;
  private Stack<String> commandHistory;
  private Pane centerPane;
  private Stage stage;
  private VBox dropdowns;
  private List<Region> mainButtons;
  private HBox turtleButtons;
  private HBox turtleMoveBox;
  private final GridPane paletteGrid;
  private Slider speedSlider;
  private boolean paused;

  public InputBox(double width, double height, ResourceBundle source) {
    textInputBox = new HBox();
    textInputBox.getStyleClass().add("input-box");
    textInputBox.setMaxSize(width, 200);
    myResources = source;

    field = new TextField();
    field.setPromptText(myResources.getString("EnterCommand"));
    field.setId("CommandField");
    field.setPrefSize(width - 1200, 300);

    paused = false;
    paletteGrid = new GridPane();
  }

  public void setFieldAction(Runnable sendCommandStringToView) {
    field.setOnAction(event -> {
      if (!field.getText().isEmpty()) {
        sendCommandStringToView.run();
      }
    });
  }

  public boolean isPaused() {
    return paused;
  }

  public void setPaused(boolean paused) {
    this.paused = paused;
  }

  public void setValues(Controller control, Animation animation, Stack<String> history, Stage stage,
      Pane centerPane) {
    this.controller = control;
    this.currAnimation = animation;
    this.commandHistory = history;
    this.stage = stage;
    this.centerPane = centerPane;
  }

  public void createSpeedSlider(double initialSpeed, Consumer<Integer> updateSpeed) {
    ChangeListener<Number> sliderListener = ((observable, oldVal, newVal) -> {
      int speed = newVal.intValue();
      if (speed == 500) {
        speed = 10000;
      }
      updateSpeed.accept(speed);
    });
    speedSlider = UserInterfaceUtil.generateSlider(10, 500, initialSpeed, sliderListener);
  }

  public void setUpTurtleMovement(Consumer<String> pushCommand) {
    TextField distanceField = new TextField("50");
    distanceField.setPrefWidth(60);
    TextField rotateField = new TextField("90");
    rotateField.setPrefWidth(60);
    turtleMoveBox = new HBox(distanceField, rotateField);
    turtleMoveBox.setTranslateY(50);
    turtleMoveBox.setPadding(new javafx.geometry.Insets(0, 0, 0, -100));

    Button leftButton = UserInterfaceUtil.generateButton("←", event -> {
      pushCommand.accept(
          myResources.getString("Left").split("\\|")[0] + " " + rotateField.getText());
    });
    Button rightButton = UserInterfaceUtil.generateButton("→", event -> {
      pushCommand.accept(
          myResources.getString("Right").split("\\|")[0] + " " + rotateField.getText());
    });
    Button forwardButton = UserInterfaceUtil.generateButton("↑", event -> {
      pushCommand.accept(
          myResources.getString("Forward").split("\\|")[0] + " " + distanceField.getText());
    });
    Button backwardButton = UserInterfaceUtil.generateButton("↓", event -> {
      pushCommand.accept(
          myResources.getString("Backward").split("\\|")[0] + " " + distanceField.getText());
    });

    VBox upDownButtons = new VBox(forwardButton, backwardButton);
    upDownButtons.setTranslateY(-15);
    upDownButtons.setSpacing(10);
    turtleButtons = new HBox(leftButton, upDownButtons, rightButton);
    turtleButtons.setPadding(new javafx.geometry.Insets(50, 0, 0, 0));

    controller.addLanguageObserver((s) -> {
      ResourceBundle newLang = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + s);
      distanceField.setPromptText(newLang.getString("DistanceField"));
      rotateField.setPromptText(newLang.getString("RotateField"));
    });
  }

  public void setUpDropdowns(List<FrontEndTurtle> turtles) {
    dropdowns = new VBox();
    dropdowns.getStyleClass().add("main-dropdowns");
    ResourceBundle defaultResources = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + "English");
    ObservableList<ComboChoice> penColors = FXCollections.observableArrayList();
    for (String color : defaultResources.getString("PenColors").split(",")) {
      penColors.add(new ComboChoice(color, color));
    }

    ComboBox<ComboChoice> colorDropDown = UserInterfaceUtil.generateComboBox(penColors, "colorBox",
        100, 300, (s) -> s, (event) -> {
          turtles.forEach(turtle -> turtle.setPenColor(Color.valueOf(event)));
        });
    colorDropDown.getOnAction().handle(new ActionEvent());

    ComboBox<ComboChoice> backgroundDropDown = UserInterfaceUtil.generateComboBox(penColors,
        "backgroundBox", 100,
        300, (s) -> s,
        (event) -> {
          centerPane.setStyle("-fx-background-color: " + event);
        });
    backgroundDropDown.setValue(null);
    dropdowns.getChildren().addAll(colorDropDown, backgroundDropDown);
  }

  public void setUpButtons(Runnable sendCommandStringToView, Runnable handleLoadTurtleImage,
      Runnable playSingleAnimation, Runnable finishCurrAnimation, Consumer<String> pushCommand) {
    Button submitField = UserInterfaceUtil.generateButton("Submit", event -> {
      sendCommandStringToView.run();
      paused = false;
    });

    Button openNewWindow = UserInterfaceUtil.generateButton("Open New Window", event -> {
      try {
        controller.openNewIdeSession(null);
        paused = false;
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    Button uploadTurtle = generateButton("UploadTurtle", (event) -> {
      handleLoadTurtleImage.run();
    });

    Button play = UserInterfaceUtil.generateButton("Play", event -> {
      paused = false;
    });
    Button pause = UserInterfaceUtil.generateButton("Pause", event -> {
      paused = true;
    });

    Button step = UserInterfaceUtil.generateButton("Step", event -> {
      if (currAnimation == null) {
        playSingleAnimation.run();
      } else {
        finishCurrAnimation.run();
      }
    });

    Button help = UserInterfaceUtil.generateButton("Help", event -> {
      Help.showHelpPopup(myResources, controller, pushCommand, field);
    });

    Button upload = UserInterfaceUtil.generateButton("Upload", event -> {
      controller.loadSession("add");
      String newSlogoContent = controller.getSlogoContent();
      pushCommand.accept(newSlogoContent);
    });

    Button save = UserInterfaceUtil.generateButton("Save", event ->
        Save.saveToFile(stage, controller, commandHistory));

    Button reset = UserInterfaceUtil.generateButton("Reset", event -> {
      try {
        controller.openNewIdeSession("");
        stage.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    mainButtons = List.of(submitField, play, pause, step, help, reset, upload, uploadTurtle,
        save, dropdowns, openNewWindow);

    mainButtons.forEach(b -> b.getStyleClass().add("main-screen-button"));

    controller.addLanguageObserver((s) -> {
      ResourceBundle newLang = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + s);
      submitField.setText(newLang.getString("Submit"));
      play.setText(newLang.getString("Play"));
      pause.setText(newLang.getString("Pause"));
      openNewWindow.setText(newLang.getString("OpenNew"));
      uploadTurtle.setText(newLang.getString("UploadTurtle"));
      step.setText(newLang.getString("Step"));
      help.setText(newLang.getString("Help"));
      save.setText(newLang.getString("Save"));
      upload.setText(newLang.getString("Upload"));
    });

  }

  public void addOtherComponentsToBox() {
    textInputBox.getChildren()
        .addAll(new VBox(speedSlider, paletteGrid), turtleMoveBox, turtleButtons, field);
  }

  public void addButtonsToBox() {
    textInputBox.getChildren().addAll(mainButtons);
  }


  public HBox getBox() {
    return textInputBox;
  }

  public TextField getField() {
    return field;
  }

  public GridPane getPaletteGrid() {
    return paletteGrid;
  }

  public void updatePalettePane(Map<Integer, List<Integer>> palette) {
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

}
