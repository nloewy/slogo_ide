package slogo.model;

import slogo.model.api.TurtleRecord;

public interface SlogoListener {

  /**
   * Called by a command that involves updating the state of the turtle
   * @param turtleState The new state of the turtle.
   *
   */
  void onUpdateTurtleState(TurtleRecord turtleState, double value);

  /**
   * Called by a command that involves setting the value of a variable.
   * @param text The text representation of the variable.
   * @param value The new value of the variable.
   */
  void onUpdateVariable(String text, double value);
}