package slogo.view;

import java.io.InputStream;
import java.util.function.Consumer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserInterfaceUtil {
    public static Button generateButton(String text,
        EventHandler<ActionEvent> consumer) {
        Button ret = new Button(text);
        ret.setId(text);
        ret.setOnAction(consumer);

        return ret;
    }

    public static Button generateButton(String text,
                                        int x,
                                        int y,
                                        EventHandler<ActionEvent> consumer) {
        Button ret = new Button(text);
        ret.setId(text);
        ret.setLayoutX(x);
        ret.setLayoutY(y);
        ret.setOnAction(consumer);

        return ret;
    }

    public static ComboBox<String> generateComboBox(ObservableList<String> options,
        int x,
        int y,
        Consumer<String> consumer) {
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setValue(options.get(0));
        comboBox.setLayoutX(x);
        comboBox.setLayoutY(y);
        comboBox.setOnAction(e -> {
            String selectedOption = comboBox.getValue();
            consumer.accept(selectedOption.replace(" ", "") + ".css");
        });

        return comboBox;
    }

    public static ImageView generateImageView(String imagePath, int x, int y) {
        InputStream imageStream = UserInterfaceUtil.class.getClassLoader().getResourceAsStream(imagePath);
        Image image = new Image(imageStream);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        return imageView;
    }

}
