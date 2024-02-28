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


public interface SlogoListener {

  /**
   * Called when a variable value is updated.
   *
   * @param variableName The name of the variable.
   */
  void onUpdateValue(String variableName, Number newValue);

  /**
   * Called by a command that involves updating the state of the turtle
   *
   * @param turtleState The new state of the turtle.
   */
  void onUpdateTurtleState(TurtleRecord turtleState);

  /**
   * Called by a command that involves resetting a turtle, given its id
   *
   * @param id The turtle id of the reset turtle.
   */
  void onResetTurtle(int id);

  /**
   * Returns the result of a nested method call
   * @param value of the outermost command in parse
   */
  void onReturn(double value);
}
