package slogo.model.api;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public interface Model {


  void parse(String commandStr)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

  /**
   * Opens a file dialog to load a new XML file.
   *
   * @param path: the path of the file to be loaded
   */
  File loadXml(String path);

  /**
   * Saves the current state of the model to an XML file. Saves the commands, variables, and turtle
   * state.
   *
   * @param path
   */
  File saveXml(String path);

  /**
   * Resets the model to its initial state.
   */
  void resetModel();
}
