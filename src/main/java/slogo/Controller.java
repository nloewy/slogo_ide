package slogo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import slogo.model.SlogoModel;
import slogo.model.api.InsufficientArgumentsException;
import slogo.model.api.InvalidTokenException;
import slogo.model.api.Model;
import slogo.view.View;
import slogo.view.pages.StartScreen;

//TODO add method to add user
//defined commands call it in parse try catch
public class Controller {

  private final Stage stage;
  private String currentLanguage = "English";
  private String currentTheme = "LightMode.css";
  private final List<Consumer<String>> languageObservers = new ArrayList<>();
  private Consumer<String> parse;
  private final List<View> windows = new ArrayList<>();
  private File turtleImage;

  public Controller(Stage stage) throws IOException {
    this.stage = stage;
    openStartScreen();
  }

  public void setTurtleImage(File i) {
    turtleImage = i;
  }

  public void openStartScreen() throws FileNotFoundException {
    StartScreen startScreen = new StartScreen(stage, this);
    startScreen.setUp();
    setCurrentLanguage(currentLanguage);
    stage.setScene(startScreen.getScene());
    stage.show();
  }

  public void openBlankIDESession() throws IOException {
    Stage newStage = new Stage();
    newStage.setMaximized(true);
    View view = new View(this, stage);
    if (turtleImage != null) {
      view.setTurtleImage(turtleImage);
    }
    Model model = new SlogoModel(view, currentLanguage);
    windows.add(view);

    parse = t -> {
      try {
        model.parse(t);
      } catch (ClassNotFoundException | InvocationTargetException | InvalidTokenException |
               NoSuchMethodException | InstantiationException | IllegalAccessException |
               NoSuchFieldException | InsufficientArgumentsException e) {
        new Alert(AlertType.ERROR, e.getMessage()).show();
      }
    };

    view.run(parse);
    setCurrentLanguage(currentLanguage);
  }

  public void addLanguageObserver(Consumer<String> observer) {
    if (observer != null) {
      languageObservers.add(observer);
    }
  }

  private void updateLanguageObservers() {
    for (Consumer<String> observer : languageObservers) {
      observer.accept(currentLanguage);
    }
  }

  public String getCurrentLanguage() {
    return currentLanguage;
  }

  public void setCurrentLanguage(String language) {
    this.currentLanguage = language;
    updateLanguageObservers();
  }

  public void updateCurrentTheme(Scene scene) {
    scene.getStylesheets().clear();
    scene.getStylesheets()
        .add(Objects.requireNonNull(View.class.getResource(currentTheme)).toExternalForm());
  }

  public void setCurrentTheme(String theme, Scene scene) {
    this.currentTheme = theme;
    updateCurrentTheme(scene);
  }

  public void loadSession() {
    System.out.println("To Be Implemented! Need to figure out how to move "
        + "file choosing from screen to controller");
  }

  public void openNewXMLSession() {
    System.out.println("To Be Implemented!");
  }

}
