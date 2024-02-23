package slogo.view.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import slogo.view.ButtonUtil;
import slogo.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/*
The loadNewScreen and loadOldScreen buttons will
open a file loader and call a method in View
that will initialize appropriately with
the XML File.
 */
public class StartScreen extends Screen {

    private final Group root;
    private final Stage stage;

    private final Image logo;
    private final ImageView logoView;

    private final ComboBox langBox;
    private final ObservableList<String> langOptions;
    private final TilePane langPane;
    private final Button loadNewScreenButton;
    private final Button loadOldScreenButton;

    public StartScreen(View view, Stage stage) throws FileNotFoundException {
        super();
        this.stage = stage;

        logo = new Image(new FileInputStream("src/main/resources/SlogoLOGO.png"));
        logoView = new ImageView(logo);

        langOptions = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
        langBox = new ComboBox<String>(langOptions);
        langPane = new TilePane(langBox);

        loadNewScreenButton = ButtonUtil.generateButton("Load New Session", 557, 232, (event) -> {
            handleLoadNewFile();
        });

        loadOldScreenButton = ButtonUtil.generateButton("Load Old Session", 557, 300, (event) -> {
            handleLoadOldFile();
        });

        root = new Group();

        root.getChildren().add(loadNewScreenButton);
        root.getChildren().add(loadOldScreenButton);
        root.getChildren().add(logoView);
        root.getChildren().add(langPane);
    }

    //This method will send the XMLFile to a View method
    //This will also initialize a MainScreen object
    private void handleLoadOldFile() {
        File dataFile = Screen.FILE_CHOOSER.showOpenDialog(stage);

        if (dataFile != null) {
            System.out.println(dataFile.getAbsolutePath());
        }
    }

    private void handleLoadNewFile() {
        System.out.println("To Be Implemented!");
    }

    @Override
    public void setUp() {
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(400);
        logoView.setLayoutX(512);
        logoView.setLayoutY(100);
    }

    @Override
    public Group getGroup() {
        return root;
    }
}

