package slogo.view.pages;

import javafx.scene.Group;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import slogo.Main;

import java.io.File;

public abstract class Screen {

    public static final FileChooser IMAGE_CHOOSER = makeImageChooser("png");

    private static FileChooser makeImageChooser(String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("fileChooserTitle");
        result.setInitialDirectory(new File(Main.DATA_FILE_FOLDER));
        result.getExtensionFilters()
                .setAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        return result;
    }

    /**
     * Set up all the buttons, keyEvents and layouts by implementing the setup methods
     */
    public abstract void setUp();

    /**
     * return the group containing all the elements of the page
     */
    public abstract Group getGroup();

}

