package slogo.model.api;

import java.io.File;

public interface Model {


  public void parse(String commandStr);

  /**
   * Opens a file dialog to load a new XML file.
   * @param path: the path of the file to be loaded
   */
  public File loadXml(String path);

  /**
   * Saves the current state of the model to an XML file.
   * Saves the commands, variables, and turtle state.
   * @param path
   */
  public File saveXml(String path);

  /**
   * Resets the model to its initial state.
   */
  public void resetModel();
}
