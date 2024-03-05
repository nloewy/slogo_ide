package slogo.view.pages;

import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VariableDialog {

  private final Stage stage = new Stage();
  private final GridPane layout = new GridPane();
  private final Map<Label, TextField> myDisplayMap = new HashMap<>();
  private final Button submitButton = new Button("Submit");
  private final Map<String, Number> variablesMap;
  MainScreen view;
  boolean isClosed = false;

  public VariableDialog(MainScreen view, Map<String, Number> variables) {
    variablesMap = variables;
    this.view = view;

    variablesMap.forEach((String label, Number value) -> {
      Label myLabel = new Label(label);
      TextField myField = new TextField("" + value);

      myDisplayMap.put(myLabel, myField);
    });

    setButtonAction();
    setLayout();
  }

  private void setButtonAction() {
    submitButton.setOnAction(e -> {
      myDisplayMap.forEach((Label myLabel, TextField myField) -> {
        variablesMap.put(myLabel.getText(), Double.parseDouble(myField.getText()));
      });

      isClosed = true;
      stage.close();
    });
  }

  public boolean display() {
    stage.initModality(Modality.APPLICATION_MODAL);
    Scene scene = new Scene(layout, 800, 400);
    stage.setTitle("Dialog");
    stage.setScene(scene);
    stage.showAndWait();
    return isClosed;
  }

  public Map<String, Number> getVariablesMap() {
    return variablesMap;
  }

  private void setLayout() {
    layout.setPadding(new Insets(10, 10, 10, 10));
    layout.setVgap(5);
    layout.setHgap(5);
    int i = 0;
    for (Map.Entry<Label, TextField> entry : myDisplayMap.entrySet()) {
      layout.add(entry.getKey(), 0, i);
      layout.add(entry.getValue(), 1, i);
      i++;
    }
    layout.add(submitButton, 1, i);

  }

}

