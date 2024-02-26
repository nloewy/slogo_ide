package slogo;

import java.io.File;
import java.io.FileNotFoundException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.view.View;
import slogo.view.ViewInternal;
import slogo.view.pages.StartScreen;

public class Controller {
    private Stage stage;
    private View view;
    private String currentLanguage = "English";

    public Controller(Stage stage) throws FileNotFoundException {
        this.stage = stage;
        this.view = new View(stage, this);
        openStartScreen();
    }

    public void openStartScreen() throws FileNotFoundException {
        StartScreen startScreen = new StartScreen(this);
        switchToScene(startScreen);
    }

    private void switchToScene(ViewInternal viewInternal) {
        viewInternal.initScene();
        stage.setScene(viewInternal.getScene());
        stage.show();
    }

    public void openNewSession() throws FileNotFoundException {
        Stage newStage = new Stage();
        View view = new View(newStage, this);
        view.run();
    }


    // This method may need to use the Model api -- loadXML and others
    public void loadSession() {
        // This opens a file chooser. Once the file is chosen, it opens a new session
        // The file is not currently used for anything
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            try {
                openNewSession();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void openNewXMLSession() {
        // For now, it does same thing as loadSession()
        loadSession();
    }

    public void handleCommand(String command) {
//         Here, implement command handling logic
//        if ("MOVE UP".equalsIgnoreCase(command)) {
//            view.moveTurtleUp(); If it was in view
//        But for otherwise it run another class that will get from model side

//        }
    }

    public void setCurrentLanguage(String language) {
        this.currentLanguage = language;
        view.setLanguage(currentLanguage);
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public View getView() {
        return view;
    }
}
