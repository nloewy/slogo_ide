package slogo.view.pages.components;

import static slogo.view.UserInterfaceUtil.generateButton;
import static slogo.view.pages.MainScreen.DEFAULT_RESOURCE_PACKAGE;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.function.Consumer;
import javafx.animation.Animation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.Controller;
import slogo.view.ComboChoice;
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
  private boolean paused;
  public InputBox(double width, double height, ResourceBundle source){
    textInputBox = new HBox();
    textInputBox.getStyleClass().add("input-box");
    textInputBox.setMaxSize(width, 200);
    myResources = source;

    field = new TextField();
    field.setPromptText(myResources.getString("EnterCommand"));
    field.setPrefSize(width - 1200, 300);

    paused = false;
  }

  public boolean isPaused(){
    return paused;
  }

  public void setPaused(boolean paused){
    this.paused = paused;
  }
  public void setValues(Controller control, Animation animation, Stack<String> history, Stage stage, Pane centerPane){
    this.controller = control;
    this.currAnimation = animation;
    this.commandHistory = history;
    this.stage = stage;
    this.centerPane = centerPane;
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

    ComboBox<ComboChoice> colorDropDown = UserInterfaceUtil.generateComboBox(penColors, "colorBox",100, 300, (s) -> s, (event) -> {
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
  }
  public void setUpButtons(Runnable sendCommandStringToView, Runnable handleLoadTurtleImage, Runnable playSingleAnimation, Runnable finishCurrAnimation, Consumer<String> pushCommand) {
    Button submitField = UserInterfaceUtil.generateButton("Submit", event -> {
      sendCommandStringToView.run();
      paused = false;
    });

    Button openNewWindow = UserInterfaceUtil.generateButton("Open New Window", event -> {
      try {
        controller.openNewIDESession(null);
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
        controller.openNewIDESession("");
        stage.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    mainButtons = List.of(submitField, play, pause, step, help, reset, upload, uploadTurtle,
        save, dropdowns, openNewWindow);

    mainButtons.forEach(b -> b.getStyleClass().add("main-screen-button"));
  }

  public void addButtonsToBox() {
    textInputBox.getChildren().addAll(mainButtons);
  }


  public HBox getBox(){
    return textInputBox;
  }

  public TextField getField(){
    return field;
  }

}
