package slogo.model.command.math;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.exceptions.TangentUndefinedFunction;
import slogo.model.node.Node;

/**
 * The TangentCommand class represents the tangent mathematical operation. It calculates the tangent
 * of a given angle in degrees.
 *
 * @author Noah Loewy
 */
public class TangentCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;

  /**
   * Constructs an instance of TangentCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public TangentCommand(ModelState modelState, SlogoListener listener) {
    //DO NOTHING
  }

  /**
   * Executes the tangent mathematical operation.
   *
   * @param arguments a list containing a single node representing the angle in degrees
   * @param turtle    the id of the turtle currently active
   * @return the tangent of the input angle
   * @throws TangentUndefinedFunction if the tangent function is undefined
   */
  @Override
  public double execute(List<Node> arguments, Turtle turtle) throws TangentUndefinedFunction {

    double arg1 = arguments.get(0).evaluate();
    if (Math.abs(arg1 % 180) == 90) {
      throw new TangentUndefinedFunction(
          "Illegal Value for Tangent Function. Function is undefined for all integer k when x = "
              + "(Pi/2) + Pi*k");
    }
    return Math.tan(Math.toRadians(arg1));
  }
}
