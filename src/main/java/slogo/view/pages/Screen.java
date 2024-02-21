package slogo.view.pages;

import javafx.scene.Group;

public abstract class Screen {

//   public static final FileChooser FILE_CHOOSER = makeChooser(Main.DATA_FILE_EXTENSION);

//   private static FileChooser makeChooser(String extensionAccepted) {
//     FileChooser result = new FileChooser();
//     result.setTitle(Main.getInternationalText("fileChooserTitle"));
//     result.setInitialDirectory(new File(Main.DATA_FILE_FOLDER));
//     result.getExtensionFilters()
//         .setAll(new FileChooser.ExtensionFilter("Files", extensionAccepted));
//     return result;
//   }

  /**
   * Set up all the buttons, keyEvents and layouts by implementing the setup methods
   */
  public abstract void setUp();

  /**
   * return the group containing all the elements of the page
   */
  public abstract Group getGroup();

}
