package slogo.view.pages;

import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import slogo.Controller;
import slogo.view.ButtonUtil;
import slogo.view.Scene;
import slogo.view.View;
import java.util.ResourceBundle;


public class MainScreen implements Scene {
    private javafx.scene.Scene scene;
    private VBox layout = new VBox(10);
    private Controller controller;
    private View view; // Assuming the View is passed to MainScreen for direct UI updates
    public static final String DEFAULT_RESOURCE_PACKAGE = "slogo.example.languages.";
    private ResourceBundle myResources;
    public MainScreen(Controller controller, View view) {
        this.controller = controller;
        this.view = view;
        String currentLanguage = controller.getCurrentLanguage();
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + currentLanguage);

        initScene();
    }

    @Override
    public void initScene() {
        Group root = new Group();
        layout.getChildren().clear(); // Clear existing children to avoid duplicates

        TextField commandInput = new TextField();
        commandInput.setPromptText("Enter SLogo command here...");
        commandInput.setLayoutX(100);
        commandInput.setLayoutY(100);

        // Setup submitButton using ButtonUtil...
        layout.getChildren().add(commandInput);
        layout.getChildren().add(ButtonUtil.generateButton(myResources.getString("Submit"), 250, 100, e -> {
            String command = commandInput.getText();
            controller.handleCommand(command); // Controller processes command
            commandInput.clear();
        }));
//        for (FrontEndTurtle turtle : View.getTurtles()) {
//            root.getChildren().add(turtle.getDisplay());
//        }
        root.getChildren().add(layout);
        scene = new javafx.scene.Scene(root, 800, 600);
    }

    @Override
    public javafx.scene.Scene getScene() {
        return scene;
    }
}

