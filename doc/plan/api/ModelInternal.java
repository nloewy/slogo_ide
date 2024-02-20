/**
 * This interface defines methods for internal operations of a model in a SLogo application.
 * These operations include creating command objects, managing commands, variables, and listeners.
 */
public interface ModelInternal {

  /**
   * Creates a command object that can be executed later by the model.
   * @param commandType a String representation of the command
   * @return A command object.
   */
  Command createCommand(String commandType);

  /**
   * Adds a command to the history.
   * @param command The command to add to the history.
   */
  void addCommandToHistory(Command command);

  /**
   * Adds a listener for receiving updates from the model.
   * @param listener The listener to add.
   */
  void addListener(SLogoListener listener);

  /**
   * Removes a listener from the model.
   * @param listener The listener to remove.
   */
  void removeListener(SLogoListener listener);
}

/**
 * This interface represents a command that can be executed in the Slogo language framework.
 */
interface Command {
  /**
   * Executes the command.
   */
  void execute();
}

/**
 * This interface defines methods for receiving updates from a Slogo model.
 */
interface SlogoListener {
  /**
   * Called when a variable value is updated.
   * @param variableName The name of the variable.
   * @param value The new value of the variable.
   */
  void onUpdateValue(String variableName, double value);

  /**
   * Called by a command that involves updating the state of the turtle
   * @param turtleState The new state of the turtle.
   *
   */
  void onUpdateTurtleState(ImmutableTurtle turtleState, double value);

  /**
   * Called by a command that involves setting the value of a variable.
   * @param text The text representation of the variable.
   * @param value The new value of the variable.
   */
  void onUpdateVariable(String text, double value);
}



