package slogo.view.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.Controller;
import static slogo.view.UserInterfaceUtil.*;
import slogo.view.View;
import slogo.view.ViewInternal;

public class StartScreen implements ViewInternal {
    private static final ObservableList<String> SUPPORTED_LANGUAGES = FXCollections.observableArrayList("English", "Spanish", "French");
    private static final ObservableList<String> SUPPORTED_THEMES = FXCollections.observableArrayList("Light Mode", "Dark Mode", "Duke Mode");
    private static final String LOGO_IMAGE_PATH = "SlogoLOGO.png";
    private Scene scene;
    private Pane root = new Pane();
    private final Controller controller;
    private Stage stage;

    public StartScreen(Stage stage, Controller controller){
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
        root.getChildren().addAll(
            generateImageView(LOGO_IMAGE_PATH, 100, 50),
            generateComboBox(SUPPORTED_LANGUAGES, 100, 200,
                controller::setCurrentLanguage),
            generateButton("Load New XML Session", 100, 300, e -> {
                controller.openNewXMLSession();
            }),
            generateButton("Load New General Session", 100, 330, e -> {
                try {
                    controller.openBlankIDESession();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }),
            generateButton("Load Old Session", 100, 360,
                e -> controller.loadSession()),
            generateButton("Upload Turtle Image", 400, 300, (event) -> {
                handleLoadTurtleImage();
            }),
            generateComboBox(SUPPORTED_THEMES, 400, 330, (event) -> {
                controller.setCurrentTheme(event, scene);
            })
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
