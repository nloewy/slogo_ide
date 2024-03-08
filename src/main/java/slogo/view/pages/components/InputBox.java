package slogo.view.pages.components;

import static slogo.view.UserInterfaceUtil.generateButton;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import slogo.Controller;
import slogo.view.UserInterfaceUtil;
import slogo.view.pages.Help;
import slogo.view.pages.Save;

public class InputBox {
  private final HBox textInputBox;
  private final ResourceBundle myResources;
  private final TextField field;
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

  public void setUpButtons(Controller control, Runnable sendCommandStringToView, Runnable handleLoadTurtleImage) {
    Button submitField = UserInterfaceUtil.generateButton("Submit", event -> {
      sendCommandStringToView.run();
      paused = false;
    });

    Button openNewWindow = UserInterfaceUtil.generateButton("Open New Window", event -> {
      try {
        control.openNewIDESession(null);
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
        playSingleAnimation();
      } else {
        finishCurrAnimation();
      }
    });

    Button help = UserInterfaceUtil.generateButton("Help", event -> {
      Help.showHelpPopup(myResources, control, this::pushCommand, field);
    });

    Button upload = UserInterfaceUtil.generateButton("Upload", event -> {
      control.loadSession("add");
      String newSlogoContent = controller.getSlogoContent();
      pushCommand(newSlogoContent);
    });

    Button save = UserInterfaceUtil.generateButton("Save", event ->
        Save.saveToFile(stage, controller, commandHistory));

    Button reset = UserInterfaceUtil.generateButton("Reset", event -> {
      try {
        fullReset();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

  }


  public HBox getBox(){
    return textInputBox;
  }

  public TextField getField(){
    return field;
  }

}
