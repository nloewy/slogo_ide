package slogo.view.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class SplashScreen extends Page {

    private final Group root;

    // private final Button loadFileButton = new
    // Button(Main.getInternationalText("loadText"));
    private final Stage stage;

    private final Image logo;
    private final ImageView logoView;

    private final ComboBox langBox;
    private final ObservableList<String> langOptions;
    private final TilePane langPane;

    public SplashScreen(Stage stage) throws FileNotFoundException {
        super();
        this.stage = stage;

        logo = new Image(new FileInputStream("src/main/resources/SlogoLOGO.png"));
        logoView = new ImageView(logo);

        root = new Group();

        langOptions = FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3");
        langBox = new ComboBox<String>(langOptions);
        langPane = new TilePane(langBox);

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
        // loadFileButton.setLayoutX(500);
        // loadFileButton.setLayoutY(300);
        // loadFileButton.setOnAction(event -> {
        // try {
        // setLoadFileButtonAction();
        // } catch (FileFieldException e) {
        // e.printStackTrace();
        // }
        // });
    }

    @Override
    public Group getGroup() {
        return root;
    }
}
