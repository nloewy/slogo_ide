package slogo.view.pages.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.function.Consumer;

public class HistoryBox {
  private final VBox commandHistoryBox;
  private final Text commandHistoryLabel;
  private final ScrollPane commandsHistory;
  private final ResourceBundle myResources;

  public HistoryBox(double width, double height, ResourceBundle source){
    commandHistoryBox = new VBox();
    commandHistoryLabel = new Text();
    commandHistoryBox.setPrefSize(400, height / 2);
    commandHistoryBox.getChildren().add(commandHistoryLabel);
    commandsHistory = new ScrollPane(commandHistoryBox);
    myResources = source;
  }

  public void setStyleClass(String style){
    commandHistoryBox.getStyleClass().add(style);
  }

  public void updateCommandBox(List<String> history, Consumer<String> pushCommand) {
    commandHistoryBox.getChildren().clear();
    commandHistoryBox.getChildren().add(commandHistoryLabel);
    for (String s : history) {
      String[] lines = s.split(" ");
      VBox vbox = new VBox();
      TitledPane titledPane = new TitledPane();
      titledPane.setText(lines[0]);
      titledPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue) {
          String fullText = String.join(" ",
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
      String[] commandParts = lines[0].split(" ");
      List<String> parameters = new ArrayList<>();
      for (String part : commandParts) {
        if (part.startsWith(":")) {
          parameters.add(part);
        }
      }
      vbox.getChildren().addAll(titledPane, openCustomCommand);
      commandHistoryBox.getChildren().add(vbox);
      Boolean hasParameters = !parameters.isEmpty();
      openCustomCommand.setOnAction(event -> {
        makeInputDialog("", "Execute This Command",
            s, "", hasParameters,
            newValue -> {
              pushCommand.accept(s);
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

  public Text getLabel(){
    return commandHistoryLabel;
  }

  public VBox getBox(){
    return commandHistoryBox;
  }

  public ScrollPane getPane(){
    return commandsHistory;
  }

}
