package slogo.view.pages;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import slogo.Controller;

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

    Map<String, Map<String, String>> commandDetails = controller.getCommandDetailsFromXML(
        languagedCommands);
    for (String command : commandDetails.keySet()) {
      Hyperlink commandLink = new Hyperlink(command);
      commandLink.setOnAction(e -> showCommandDetailsPopup(command, commandDetails.get(command), myResources, pushCommand, field));
      content.getChildren().add(commandLink);
    }

    content.getChildren().add(closeButton);
    // popup.getContent().add(content);

    helpPopupModality(popup, content);
    // popup.show(root.getScene().getWindow());
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
      pushCommand.accept(command + " " + parametersField.getText());
      field.clear();
    });

    content.getChildren().addAll(commandLabel, descriptionLabel, exampleLabel, parametersBox,
        returnValueLabel, closeButton);
//        returnValueLabel, closeButton, executeButton);
    // popup.getContent().add(content);

    helpPopupModality(popup, content);
  }

  private static void helpPopupModality(Stage popup, VBox content) {
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

}

class Delta {
  double x, y;
}
