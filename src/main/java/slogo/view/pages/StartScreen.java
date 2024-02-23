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

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StartScreen extends Screen {

    private final Group root;

    // private final Button loadFileButton = new
    // Button(Main.getInternationalText("loadText"));
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
            System.out.println("Load New Session");
        });

        loadOldScreenButton = ButtonUtil.generateButton("Load Old Session", 557, 300, (event) -> {
            System.out.println("Load Old Session");
        });

        root = new Group();

        root.getChildren().add(loadNewScreenButton);
        root.getChildren().add(loadOldScreenButton);
        root.getChildren().add(logoView);
        root.getChildren().add(langPane);
    }

    // private void setLoadFileButtonAction() throws FileFieldException {
    // File dataFile = Page.FILE_CHOOSER.showOpenDialog(stage);
    // Simulation simulation = View.createSimulation(dataFile);
    // if (simulation != null) {
    // Main.setLanguage(simulation.getConfig().getLanguage());
    // view.toSimulationPage(simulation,
    // simulation.getConfig().getStateColor().getStateColorMap());
    // }
    // }

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

