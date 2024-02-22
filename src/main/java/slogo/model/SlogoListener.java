package slogo.model;

import slogo.model.api.TurtleRecord;

public interface SlogoListener {
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
  void onUpdateTurtleState(TurtleRecord turtleState, double value);

  void onResetTurtle(int id);
}