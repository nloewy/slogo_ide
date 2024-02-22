package slogo;

import javafx.scene.Scene;
import javafx.stage.Stage;
import slogo.view.View;
import slogo.view.pages.MainScreen;
import slogo.view.pages.Screen;
import slogo.view.pages.StartScreen;

import java.io.FileNotFoundException;
import java.util.Objects;

public class Controller {

    private static final int height = 600;
    private static final int width = 1000;
    private final Stage stage;
    private final View view;

    public Controller(Stage stage) {
        this.stage = stage;
        view = new View(stage);
    }

    public void run() throws FileNotFoundException {
        Screen page = new StartScreen(stage);
        page.setUp();

        Scene scene = new Scene(page.getGroup(), width, height);
        scene.getStylesheets().add(Objects.requireNonNull(View.class.getResource("LightMode.css")).toExternalForm());

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
