package slogo.model.api;


/**
 * The SlogoListener interface defines a set of methods to be implemented by classes that wish to
 * receive notifications about events in the Slogo Model. These events include variable value
 * updates, turtle state changes, turtle resets, parse operation results, and user-defined command
 * notifications.
 *
 * @author Noah Loewy
 */

public interface SlogoListener {

  /**
   * Invoked when a variable value is updated.
   *
   * @param variableName The name of the updated variable.
   * @param newValue     The new value of the variable.
   */
  void onUpdateValue(String variableName, Number newValue);

  /**
   * Invoked when the state of the turtle is updated.
   *
   * @param turtleState The new state of the turtle.
   */
  void onUpdateTurtleState(TurtleRecord turtleState);

  /**
   * Invoked when a turtle is reset.
   *
   * @param id The ID of the turtle being reset.
   */
  void onResetTurtle(int id);

  /**
   * Invoked at the end of a parse operation to relay the result.
   *
   * @param value  The numerical result of the parse operation.
   * @param string The formatted string representing the parse operation.
   */
  void onReturn(double value, String string);

  /**
   * Invoked to provide the listener with formatted user-defined commands.
   *
   * @param string The formatted user-defined command.
   */
  void onUserDefinedCommand(
      String string); // Highlights if it is a user-defined command or a history command.
}
