package slogo.view.pages;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import slogo.view.Controller;

/**
 * The Help class is a page that is displayed when the user clicks the help button. It provides
 * information about the commands that the user can use in the Slogo environment.
 */

public class Help {

  public static void showHelpPopup(ResourceBundle myResources, Controller controller,
      Consumer<String> pushCommand, TextField field) {
    // Popup popup = new Popup();

    Stage popup = new Stage();

    popup.initStyle(StageStyle.UTILITY);
    popup.initModality(Modality.APPLICATION_MODAL);
    VBox content = new VBox(10);
    content.setStyle("-fx-background-color: white; -fx-padding: 10;");

    Button closeButton = new Button(myResources.getString("Close"));
    closeButton.setOnAction(e -> popup.hide());

    String languagedCommands = myResources.getString("commands");

    Map<String, Map<String, String>> commandDetails = controller.getCommandDetailsFromXml(
        languagedCommands);
//    int commandCounter = 0;
    for (String command : commandDetails.keySet()) {
//      commandCounter++;
      Hyperlink commandLink = new Hyperlink(command);
      commandLink.setOnAction(
          e -> showCommandDetailsPopup(command, commandDetails.get(command), myResources,
              pushCommand, field));
      content.getChildren().add(commandLink);
//      System.out.print(commandCounter);
    }

    content.getChildren().add(closeButton);
    helpPopupModality(popup, content);

  }

  private static void showCommandDetailsPopup(String command, Map<String, String> details,
      ResourceBundle myResources, Consumer<String> pushCommand, TextField field) {
    Stage popup = new Stage();
    popup.initStyle(StageStyle.UTILITY);
    popup.initModality(Modality.APPLICATION_MODAL);

    VBox content = new VBox(10);
    content.setStyle("-fx-background-color: white; -fx-padding: 10;");

    Label commandLabel = new Label(myResources.getString("Command") + ": " + command);
    Label descriptionLabel = new Label(
        myResources.getString("Description") + ": " + details.get("description"));

    content.getChildren().addAll(commandLabel, descriptionLabel);

    if (details.get("example") != null) {
      Label exampleLabel = new Label(
          myResources.getString("Example") + ": " + details.get("example"));
      content.getChildren().add(exampleLabel);
    }

    HBox parametersBox = new HBox(10);
    if (details.get("parameters") != null) {
      Label parametersLabel = new Label(
          myResources.getString("Parameters") + ": " + details.get("parameters"));
      parametersBox.getChildren().addAll(parametersLabel);
    }

    TextField parametersField = new TextField();
    parametersBox.getChildren().addAll(parametersField);
    Label returnValueLabel = new Label(
        myResources.getString("ReturnValue") + ": " + details.get("returnValue"));

    Button closeButton = new Button(myResources.getString("Close"));
    closeButton.setOnAction(e -> popup.hide());

    Button executeButton = new Button(myResources.getString("Execute"));
    executeButton.setOnAction(e -> {
      pushCommand.accept(command + " " + parametersField.getText());
      field.clear();
    });

    content.getChildren().addAll(parametersBox,
        returnValueLabel, closeButton, executeButton);
//        returnValueLabel, closeButton, executeButton);
    // popup.getContent().add(content);

    helpPopupModality(popup, content);
  }

  private static void helpPopupModality(Stage popup, VBox content) {
    AtomicReference<Double> dragDeltaX = new AtomicReference<>((double) 0);
    AtomicReference<Double> dragDeltaY = new AtomicReference<>((double) 0);

    // Wrap content in a ScrollPane
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(content);
    scrollPane.setFitToWidth(true); // Make the scroll pane fit the width of the content
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER); // Hide horizontal scroll bar
    scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED); // Show vertical scroll bar as needed

    content.setOnMousePressed(mouseEvent -> {
      dragDeltaX.set(popup.getX() - mouseEvent.getScreenX());
      dragDeltaY.set(popup.getY() - mouseEvent.getScreenY());
    });
    content.setOnMouseDragged(mouseEvent -> {
      popup.setX(mouseEvent.getScreenX() + dragDeltaX.get());
      popup.setY(mouseEvent.getScreenY() + dragDeltaY.get());
    });

    Scene scene = new Scene(scrollPane); // Set the scene to use the scroll pane
    popup.setScene(scene);
    popup.show();
  }

}