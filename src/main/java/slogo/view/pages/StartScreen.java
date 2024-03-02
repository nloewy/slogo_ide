package slogo.view.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import slogo.Controller;
import slogo.view.ButtonUtil;
import slogo.view.ViewInternal;

public class StartScreen implements ViewInternal {
    private javafx.scene.Scene scene;
    private Group root = new Group();
    private final Controller controller;
    private Stage stage;

    public StartScreen(Stage stage, Controller controller) throws FileNotFoundException {
        this.controller = controller;
        this.stage = stage;

        Image logo = new Image(new FileInputStream("src/main/resources/SlogoLOGO.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(400);
        logoView.setLayoutX(100);
        logoView.setLayoutY(50);

        ObservableList<String> langOptions = FXCollections.observableArrayList("English",
            "Spanish", "French");
        ComboBox<String> langBox = new ComboBox<>(langOptions);
        langBox.setLayoutX(100);
        langBox.setLayoutY(200);

        // Controller gets language
        langBox.setOnAction(e -> {
            String selectedLanguage = langBox.getValue();
            controller.setCurrentLanguage(selectedLanguage);
        });

        root.getChildren().addAll(
            ButtonUtil.generateButton("Load New XML Session", 100, 300, e -> {
              controller.openNewXMLSession();
            }),
            ButtonUtil.generateButton("Load New General Session", 100, 330, e -> {
                try {
                    controller.openBlankIDESession();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }),
            ButtonUtil.generateButton("Load Old Session", 100, 360,
                e -> controller.loadSession()),
            ButtonUtil.generateButton("Upload Turtle Image", 400, 300, (event) -> {
                handleLoadTurtleImage();
            }),
            ButtonUtil.generateButton("Light Mode", 400, 330, (event) -> {
                handleLoadTurtleImage();
            }),
            ButtonUtil.generateButton("Dark Mode", 400, 360, (event) -> {
                handleLoadTurtleImage();
            }),
            logoView,
            langBox
        );

        scene = new javafx.scene.Scene(root, 600, 400);
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
        return root;
    }

    @Override
    public void setUp() {
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
