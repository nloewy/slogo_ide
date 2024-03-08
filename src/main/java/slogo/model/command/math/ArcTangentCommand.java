package slogo.model.command.math;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The ArcTangentCommand class represents the arctangent mathematical operation. It calculates the
 * arctangent (inverse tangent) of a given number and returns the result in degrees.
 *
 * @author Noah Loewy
 */

public class ArcTangentCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;

  /**
   * Constructs an instance of ArcTangentCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public ArcTangentCommand(ModelState modelState, SlogoListener listener) {
    //DO NOTHING
  }

  /**
   * Executes the arctangent mathematical operation.
   *
   * @param arguments a list containing a single node representing the number to calculate the
   *                  arctangent of
   * @param turtle    the id of the turtle currently active
   * @return the arctangent of the input number in degrees
   */
  @Override
  public double execute(List<Node> arguments, Turtle turtle) {
    double arg1 = arguments.get(0).evaluate();
    return Math.atan(arg1) * 180 / Math.PI;
  }
}
