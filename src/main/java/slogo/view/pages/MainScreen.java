package slogo.view.pages;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import slogo.view.ButtonUtil;
import slogo.view.View;

import java.io.FileNotFoundException;

public class MainScreen extends Screen {

    Stage stage;
    Group root;

    TextField field;
    Button submitField;

    public MainScreen(Stage stage) throws FileNotFoundException {
        super();
        this.stage = stage;

        field = new TextField();
        submitField = ButtonUtil.generateButton("submit", 251, 100, event -> {
            View.setCommandString(field.getText());
            field.clear();
        });

        root = new Group();
        root.getChildren().add(field);
        root.getChildren().add(submitField);

        // scene.getStylesheets().add("src/main/resources/slogo/css/LightMode.css");
    }

    @Override
    public void setUp() {
        field.setLayoutX(100);
        field.setLayoutY(100);
    }

    @Override
    public Group getGroup() {
        return root;
    }

}