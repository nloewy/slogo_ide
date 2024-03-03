package slogo.view;

import java.io.FileNotFoundException;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Controller;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest extends DukeApplicationTest {
  private Controller controller;
  private Stage stage;

  @BeforeEach
  public void setup() throws Exception {
    // Set up the JavaFX environment using DukeApplicationTest
    stage = new Stage();
    controller = new Controller(stage);
    // Make sure you are running on the JavaFX thread
    interact(() -> {});
  }

  @Test
  public void testOpensStartScreenOnInitialization() {
    // Assertions must be run on the JavaFX Application Thread
    interact(() -> assertNotNull(stage.getScene()));
  }

  @Test
  public void testOpensNewSessionCreatesNewView() throws FileNotFoundException {
    // Simulate opening a new session
    interact(() -> {
      try {
        controller.openNewSession();
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    });
    // Check if a new view is created
    interact(() -> {
      View newView = controller.getView();
      assertNotNull(newView);
      assertNotEquals(newView, controller.getView());
    });
  }

  @Test
  public void testSetCurrentLanguageUpdatesViewLanguage() {
    // Simulate setting the current language
    interact(() -> {
      String newLanguage = "Spanish";
      controller.setCurrentLanguage(newLanguage);
      // Check if the view's language is updated
      assertEquals(newLanguage, controller.getCurrentLanguage());
    });
  }

  @Test
  public void testGetCurrentLanguageReturnsCurrentLanguage() {
    // Get the current language
    interact(() -> {
      String language = controller.getCurrentLanguage();
      // Check if the returned language is correct
      assertEquals("English", language);
    });
  }

  // More test methods go here...

  // Utility methods from DukeApplicationTest can be used to simulate user interactions
  // For example, clicking buttons, typing into text fields, etc.

  // Remember to override any necessary methods from DukeApplicationTest if needed
}
