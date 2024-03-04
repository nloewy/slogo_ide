package slogo.model.api;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import slogo.model.exceptions.InvalidTokenException;

public interface Model {


  void parse(String commandStr)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException, InvalidTokenException;

  /**
   * Opens a file dialog to load a new Slogo file.
   *
   * @param path: the path of the file to be loaded
   */
  File loadSlogo(String path);

  /**
   * Saves the current state of the model to a Slogo file. Saves the commands, variables, and turtle
   * state.
   *
   * @param path
   */
  File saveSlogo(String path);

  /**
   * Resets the model to its initial state.
   */
  void resetModel();
}
