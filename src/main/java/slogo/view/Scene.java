package slogo.view;

import javafx.scene.Group;

public interface Scene {
  void initScene();
  javafx.scene.Scene getScene();
  Group getGroup();

  void setUp();
}
