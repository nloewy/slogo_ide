package slogo.view.pages;

import javafx.scene.Group;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public abstract class Screen {

  public static final FileChooser IMAGE_CHOOSER = makeImageChooser("png");

  private static FileChooser makeImageChooser(String extensionAccepted) {
    FileChooser result = new FileChooser();
    result.setTitle("fileChooserTitle");
    result.getExtensionFilters()
        .setAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
    return result;
  }

  public abstract void setUp();

  public abstract Group getGroup();
}

