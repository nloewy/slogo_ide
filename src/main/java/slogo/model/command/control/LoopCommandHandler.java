package slogo.model.command.control;

import slogo.model.ModelState;
import slogo.model.node.Node;

/**
 * The LoopCommandHandler class is responsible for executing a loop with the specified parameters and commands.
 * It iterates over a range of values and evaluates the commands for each value of the loop variable.
 *
 * @author Noah Loewy
 */
public class LoopCommandHandler {

  private double start;
  private double end;
  private double increment;

  /**
   * Sets the parameters for the loop: start value, end value, and increment value.
   *
   * @param start     The starting value of the loop.
   * @param end       The ending value of the loop.
   * @param increment The increment value for each iteration of the loop.
   */
  public void setLoopParameters(double start, double end, double increment) {
    this.start = start;
    this.end = end;
    this.increment = increment;
  }

  /**
   * Sets the parameters for the loop with default increment of 1.
   *
   * @param end The ending value of the loop.
   */
  public void setLoopParameters(double end) {
    this.setLoopParameters(1, end, 1);
  }

  /**
   * Executes the loop with the specified commands for each value of the loop variable.
   *
   * @param commands     The commands to be executed in the loop.
   * @param modelState   The model state.
   * @param variableName The name of the loop variable.
   * @return The result of the last evaluated command in the loop.
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
