package slogo.view;

import javafx.scene.Group;

public interface ViewInternal {

  javafx.scene.Scene getScene();

  Group getGroup();

  void setUp();
}
