package slogo.view.pages;

import static slogo.view.UserInterfaceUtil.generateButton;
import static slogo.view.UserInterfaceUtil.generateComboBox;
import static slogo.view.UserInterfaceUtil.generateImageView;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.Controller;
import slogo.view.ViewInternal;

public class StartScreen implements ViewInternal {

  private static final String DEFAULT_RESOURCE_PACKAGE = "slogo.example.languages.";
  private static final ResourceBundle LANG_OPT_BUNDLE = ResourceBundle.getBundle(
      DEFAULT_RESOURCE_PACKAGE + "LanguageOptions");
  private static final ObservableList<String> SUPPORTED_LANGUAGES =
      FXCollections.observableArrayList(
          LANG_OPT_BUNDLE.keySet().stream().map(LANG_OPT_BUNDLE::getString).toList());
  private static final String LOGO_IMAGE_PATH = "SlogoLOGO.png";
  private final Controller controller;
  private Scene scene;
  private final Pane root = new Pane();
  private final Stage stage;

  public StartScreen(Stage stage, Controller controller) {
    this.controller = controller;
    this.stage = stage;
  }

  private void handleLoadTurtleImage() {
    File dataFile = Screen.IMAGE_CHOOSER.showOpenDialog(stage);
    controller.setTurtleImage(dataFile);
  }

  @Override
  public javafx.scene.Scene getScene() {
    return scene;
  }

  @Override
  public Group getGroup() {
    return null;
  }

  @Override
  public void setUp() {
    ResourceBundle myResources = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + controller.getCurrentLanguage());
    Button loadSlogo = generateButton("LoadSlogo", 100, 300, e -> {
      controller.openNewXMLSession();
    });
    Button loadGen = generateButton("LoadGen", 100, 330, e -> {
      try {
        controller.openBlankIDESession();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });
    Button loadOld = generateButton("LoadOld", 100, 360, e -> controller.loadSession());
    Button uploadTurtle = generateButton("UploadTurtle", 400, 300, (event) -> {
      handleLoadTurtleImage();
    });

    ObservableList<String> supportedThemes = FXCollections.observableArrayList(
        myResources.getString("ColorThemes").split(","));
    ComboBox themeComboBox = generateComboBox(supportedThemes, 400, 330, (s) -> {
      return s.replace(" ", "") + ".css";
    }, (event) -> {
      controller.setCurrentTheme(event, scene);
    });

    controller.addLanguageObserver((s) -> {
      ResourceBundle newLang = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + s);
      loadSlogo.setText(newLang.getString("LoadSlogo"));
      loadGen.setText(newLang.getString("LoadGen"));
      loadOld.setText(newLang.getString("LoadOld"));
      uploadTurtle.setText(newLang.getString("UploadTurtle"));
//            themeComboBox.setItems(FXCollections.observableArrayList(newLang.getString("ColorThemes").split(",")));
    });

    root.getChildren().addAll(
        generateImageView(LOGO_IMAGE_PATH, 100, 50),
        generateComboBox(SUPPORTED_LANGUAGES, 100, 200,
            (s) -> {
              return LANG_OPT_BUNDLE.keySet().stream()
                  .filter(key -> LANG_OPT_BUNDLE.getString(key).equals(s)).findFirst().get();
            },
            controller::setCurrentLanguage),
        loadSlogo,
        loadGen,
        loadOld,
        uploadTurtle,
        themeComboBox
    );
    scene = new Scene(root, 600, 400);

    controller.updateCurrentTheme(scene);
  }

}

//private void handleLoadOldFile() {
//    File dataFile = Screen.FILE_CHOOSER.showOpenDialog(stage);
//
//    if (dataFile != null) {
//        System.out.println(dataFile.getAbsolutePath());
//    }
//}
//
//private void handleLoadNewFile() {
//    System.out.println("To Be Implemented!");
//}
