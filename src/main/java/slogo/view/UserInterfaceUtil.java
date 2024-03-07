package slogo.view;

import java.io.InputStream;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserInterfaceUtil {

  public static Button generateButton(String text,
      EventHandler<ActionEvent> consumer) {
    Button ret = new Button();
    ret.setText(text);
    ret.setId(text);
    ret.setOnAction(consumer);

    return ret;
  }

  public static Button generateButton(String text,
      int x,
      int y,
      EventHandler<ActionEvent> consumer) {
    Button ret = new Button();
    ret.setId(text);
    ret.setLayoutX(x);
    ret.setLayoutY(y);
    ret.setOnAction(consumer);

    return ret;
  }

  public static ComboBox<String> generateComboStringBox(ObservableList<String> options,
      String id,
      int x,
      int y,
      Function<String, String> fixString,
      Consumer<String> consumer) {
    ComboBox<String> comboBox = new ComboBox<>(options);
    comboBox.setValue(options.get(0));
    comboBox.setId(id);
    comboBox.setLayoutX(x);
    comboBox.setLayoutY(y);
    comboBox.setOnAction(e -> {
      String selectedOption = comboBox.getValue();
      consumer.accept(fixString.apply(selectedOption));
    });

    return comboBox;
  }

  public static ComboBox<ComboChoice> generateComboBox(ObservableList<ComboChoice> options,
      String id,
      int x,
      int y,
      Function<String, String> fixString,
      Consumer<String> consumer) {
    ComboBox<ComboChoice> comboBox = new ComboBox<>();
    comboBox.setItems(options);
    comboBox.setValue(options.get(0));
    comboBox.setId(id);
    comboBox.setLayoutX(x);
    comboBox.setLayoutY(y);
    comboBox.setOnAction(e -> {
      ComboChoice selectedOption = comboBox.getValue();
      if (selectedOption != null) {
        consumer.accept(fixString.apply(selectedOption.getValue()));
      }
    });

    return comboBox;
  }


  public static ImageView generateImageView(String imagePath, int x, int y) {
    InputStream imageStream = UserInterfaceUtil.class.getClassLoader()
        .getResourceAsStream(imagePath);
    Image image = new Image(imageStream);
    ImageView imageView = new ImageView(image);
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(400);
    imageView.setLayoutX(x);
    imageView.setLayoutY(y);
    return imageView;
  }

  public static Slider generateSlider(double min, double max, double val, ChangeListener<Number> sliderListener) {
    Slider userSlider = new Slider();
    userSlider.setMin(min);
    userSlider.setMax(max);
    userSlider.setValue(val);
    userSlider.setShowTickLabels(true);
    userSlider.setShowTickMarks(true);
    userSlider.setMajorTickUnit(10);
    userSlider.valueProperty().addListener(sliderListener);
    return userSlider;
  }

}
