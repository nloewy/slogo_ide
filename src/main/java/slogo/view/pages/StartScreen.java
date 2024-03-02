package slogo.view.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import slogo.Controller;
import slogo.view.ButtonUtil;
import slogo.view.ViewInternal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StartScreen implements ViewInternal {
    private javafx.scene.Scene scene;
    private Group root = new Group();
    private final Controller controller;

    public StartScreen(Controller controller) throws FileNotFoundException {
        this.controller = controller;

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
            logoView,
            langBox
        );

        scene = new javafx.scene.Scene(root, 600, 400);
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
