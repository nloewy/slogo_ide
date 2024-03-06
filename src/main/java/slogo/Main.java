package slogo;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import slogo.model.exceptions.DivideByZeroException;
import slogo.model.exceptions.LogOfNegativeException;
import slogo.model.exceptions.TangentUndefinedFunction;
import slogo.model.exceptions.UndefinedExponentException;

public class Main extends Application {

  public static final String DEFAULT_LANGUAGE = "English";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws FileNotFoundException {
    stage.setTitle("SLogo");
    try {
      Controller controller = new Controller(stage);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
