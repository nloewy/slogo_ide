public interface ModelExternal {

  /**
   * Parses the command string and executes the command.
   * Will be called in controller and model to parse commannd from XML and user input
   * @param commandStr
   */
  public void parse(String commandStr);

  /**
   * Resets the model to its initial state.
   */
  public void resetModel();
}


