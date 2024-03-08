package slogo.model.command.control;

import slogo.model.ModelState;
import slogo.model.node.Node;

/**
 * The LoopCommandHandler class provides functionality for executing a loop of commands with
 * specified parameters. It allows setting the loop parameters and running the loop with the
 * specified commands, model state, and variable name.
 *
 * @author Noah Loewy
 */

public class LoopCommandHandler {

  private double start;
  private double end;
  private double increment;

  /**
   * Sets the parameters for the loop.
   *
   * @param start     the starting value of the loop
   * @param end       the ending value of the loop
   * @param increment the increment value for each iteration of the loop
   */

  public void setLoopParameters(double start, double end, double increment) {
    this.start = start;
    this.end = end;
    this.increment = increment;
  }

  /**
   * Sets the loop parameters with a default increment of 1.
   *
   * @param end the ending value of the loop
   */
  public void setLoopParameters(double end) {
    this.setLoopParameters(1, end, 1);
  }

  /**
   * Runs the loop with the specified commands, model state, and variable name. Executes commands
   * then returns the value to the command that called it.
   *
   * @param commands     the commands to be executed in the loop
   * @param modelState   the model state
   * @param variableName the name of the loop variable
   * @return the result of the last evaluated command in the loop
   */
  public double runLoop(Node commands, ModelState modelState, String variableName) {
    double res = 0.0;
    for (double idx = start; idx <= end; idx += increment) {
      modelState.getVariables().put(variableName, idx);
      res = commands.evaluate();
      modelState.getVariables().remove(variableName);
    }
    return res;
  }
}