package slogo.view;

import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.stage.Stage;
import slogo.view.pages.Page;
import slogo.view.pages.SplashScreen;

public class View implements SlogoListener {

    private static final int height = 600;
    private static final int width = 1000;  

    private static Page page;
    private static Stage stage;
    private static Scene scene;

    public View(Stage stage) {
        View.stage = stage;
    }

    public void run() throws FileNotFoundException {
        page = new SplashScreen(stage);
        page.setUp();

        scene = new Scene(page.getGroup(), width, height);
        scene.getStylesheets().add(View.class.getResource("LightMode.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
