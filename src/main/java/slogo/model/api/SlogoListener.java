package slogo.model.api;

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

  void onResetTurtle(int id);

  void onReturn(double value);
}