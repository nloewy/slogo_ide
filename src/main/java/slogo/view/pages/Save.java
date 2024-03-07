package slogo.view.pages;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.Controller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
public class Save {

  public static void saveToFile(Stage stage, Controller controller, Stack<String> commandHistory) {
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
        savePreferences(stage, controller);
      } else if (response == buttonTypeTwo) {
        saveCommandsToFile(stage, commandHistory);
      } else if (response == closeButtonType) {
        alert.close();
      }
    });
  }

  public static void savePreferences(Stage stage, Controller controller) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
    File file = fileChooser.showSaveDialog(stage);
    if (file != null) {
      try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
        writer.println("<preferences>");
        writer.println("  <language>" + controller.getCurrentLanguage() + "</language>");
//        writer.println("  <theme>" + controller.getCurrentTheme() + "</theme>");
        writer.println("  <theme>" + "IDK TRYING TO GET" + "</theme>");
        writer.println("  <penColor>" + "PEN COLOR" + "</penColor>");
        writer.println("</preferences>");

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void saveCommandsToFile(Stage stage, Stack<String> commandHistory) {
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

}
