package slogo.view;

import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.stage.Stage;
import slogo.view.pages.Page;
import slogo.view.pages.SplashScreen;

public class View {

    private static final int height = 600;
    private static final int width = 1000;  

    private Page page;
    private Stage stage;

    public View(Stage stage) {
        this.stage = stage;
    }

    public void run() throws FileNotFoundException {
        page = new SplashScreen(stage);
        page.setUp();
        stage.setScene(new Scene(page.getGroup(), width, height));
        stage.setMaximized(true);
        stage.show();
    }
}
