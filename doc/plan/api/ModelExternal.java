public interface ModelExternal {

  /**
   * Parses the command string and executes the command.
   * Will be called in controller and model to parse commannd from XML and user input
   * @param commandStr
   */
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
