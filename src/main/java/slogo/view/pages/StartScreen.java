package slogo.view.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import slogo.Controller;
import slogo.view.ButtonUtil;
import slogo.view.Scene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StartScreen implements Scene {
    private javafx.scene.Scene scene;
    private Group root = new Group();
    private Controller controller;

    public StartScreen(Controller controller) throws FileNotFoundException {
        this.controller = controller;

        Image logo = new Image(new FileInputStream("src/main/resources/SlogoLOGO.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(400);
        logoView.setLayoutX(100);
        logoView.setLayoutY(50);

        ObservableList<String> langOptions = FXCollections.observableArrayList("English", "Spanish", "French");
        ComboBox<String> langBox = new ComboBox<>(langOptions);
        langBox.setLayoutX(100);
        langBox.setLayoutY(200);

        // Controller gets language
        langBox.setOnAction(e -> {
            String selectedLanguage = langBox.getValue();
            controller.setCurrentLanguage(selectedLanguage);
        });

        root.getChildren().addAll(
            ButtonUtil.generateButton("Load New XML Session", 100, 300, e -> controller.openNewSession()),
            ButtonUtil.generateButton("Load New General Session", 100, 330, e -> controller.openNewSession()),
            ButtonUtil.generateButton("Load Old Session", 100, 360, e -> controller.loadSession()), // Uncomment or modify based on your implementation
            logoView,
            langBox
        );

        scene = new javafx.scene.Scene(root, 600, 400);
    }

    @Override
    public void initScene() {
    }

    @Override
    public javafx.scene.Scene getScene() {
        return scene;
    }
}
