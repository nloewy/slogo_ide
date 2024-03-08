package slogo.model.command.control;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The LoopCommandHandler class is responsible for executing a loop with the specified parameters
 * and commands. It iterates over a range of values and evaluates the commands for each value of the
 * loop variable.
 *
 * @author Noah Loewy
 */
public abstract class LoopCommand implements Command {

  public abstract double execute(List<Node> arguments, Turtle turtle);

  /**
   * Sets the parameters for the loop: start value, end value, and increment value.
   * Executes the loop with the specified commands for each value of the loop variable.
   *
   * @param start     The starting value of the loop.
   * @param end       The ending value of the loop.
   * @param increment The increment value for each iteration of the loop.
   * @param commands     The commands to be executed in the loop.
   * @param modelState   The model state.
   * @param variableName The name of the loop variable.
   * @return The result of the last evaluated command in the loop.
   */

  protected double runLoop(double start, double end, double increment, Node commands,
      ModelState modelState, String variableName) {
    double res = 0.0;
    for (double idx = start; idx <= end; idx += increment) {
      modelState.getVariables().put(variableName, idx);
      res = commands.evaluate();
      modelState.getVariables().remove(variableName);
    }
    return res;
  }
}
