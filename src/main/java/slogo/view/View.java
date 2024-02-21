package slogo.view;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.model.SlogoListener;
import slogo.model.api.TurtleRecord;
import slogo.view.pages.Page;
import slogo.view.pages.SplashScreen;

public class View implements SlogoListener {

    private static final int height = 600;
    private static final int width = 1000;  

    private static Page page;
    private static Stage stage;
    private static Scene scene;

    private Map<String, Number> variables;

    private String lang;

    public View(Stage stage) {
        View.stage = stage;

        lang = "EG";
        variables = new HashMap<>();
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

    @Override
    public void onUpdateVariable(String variableName, double value) {
        variables.remove(variableName);
        variables.put(variableName, value);
    }

    @Override
    public void onUpdateValue(String text, double value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onUpdateValue'");
    }

    @Override
    public void onUpdateTurtleState(TurtleRecord turtleState, double value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onUpdateTurtleState'");
    }

    /*
   * Handles the user switching languages.
   * //TODO
   */
  public void setLanguage(String lang) {
    this.lang = lang;
  }

  /*
   * Handles the user switching background color.
   */ //TODO Test
  public void setColor(Color color) {
    View.stage.getScene().setFill(color);
  }

//   /*
//    * Checks if the text field has at least
//    * one valid command in it.
//    */
//   public boolean hasCommandString();

//   /*
//    * Returns the current text field body.
//    */
//   public String getCommandString();

//   /*
//    * Gets the current exception, and shows
//    * it as an alert.
//    */
//   public void displayErrorMessage();

//   /*
//    * Gets the current settings, and shows
//    * it as a panel.
//    */
//   public void displaySettingsPanel();

//   /*
//    * Gets the current XMl file, and shows
//    * it as an panel.
//    */
//   public void displaySavePanel();

//   /*
//    * Gets the current documentation, and shows
//    * it as an panel.
//    */
//   public void displayHelpPanel();

//   /*
//    * Gets the current command history, and shows
//    * it as an panel.
//    */
//   public void displayHistoryPanel();

//   /**
//    * Resets all panels in the view
//    */
//   public void resetView();
}
