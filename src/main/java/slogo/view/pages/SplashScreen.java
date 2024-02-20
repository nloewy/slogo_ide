package slogo.view.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class SplashScreen extends Page {

  private final Group root;

//   private final Button loadFileButton = new Button(Main.getInternationalText("loadText"));
  private final Stage stage;

  private final Image logo;
  private final ImageView logoView;

  public SplashScreen(Stage stage) throws FileNotFoundException {
    super();
    this.stage = stage;

    logo = new Image(new FileInputStream("src/main/resources/SlogoLOGO.png"));
    logoView = new ImageView(logo);

    root = new Group();
    root.getChildren().add(logoView);

    // root.getChildren().add(loadFileButton);
  }

//   private void setLoadFileButtonAction() throws FileFieldException {
//     File dataFile = Page.FILE_CHOOSER.showOpenDialog(stage);
//     Simulation simulation = View.createSimulation(dataFile);
//     if (simulation != null) {
//       Main.setLanguage(simulation.getConfig().getLanguage());
//       view.toSimulationPage(simulation, simulation.getConfig().getStateColor().getStateColorMap());
//     }
//   }

  @Override
  public void setUp() {
    // loadFileButton.setLayoutX(500);
    // loadFileButton.setLayoutY(300);
    // loadFileButton.setOnAction(event -> {
    //   try {
    //     setLoadFileButtonAction();
    //   } catch (FileFieldException e) {
    //     e.printStackTrace();
    //   }
    // });
  }

  @Override
  public Group getGroup() {
    return root;
  }
}
