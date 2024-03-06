package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
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
  private final ModelState modelState;

  /**
   * Constructs an instance of ArcTangentCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public ArcTangentCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;

  }

  /**
   * Executes the arctangent mathematical operation.
   *
   * @param arguments a list containing a single node representing the number to calculate the
   *                  arctangent of
   * @param index     the index of the turtle in the list at the top of getActiveTurtles() stack
   * @return the arctangent of the input number in degrees
   */
  @Override
  public double execute(List<Node> arguments, int index)
       {
    modelState.setOuter(false);
    double arg1 = arguments.get(0).evaluate();
    return MathUtils.toDegrees(Math.atan(arg1));
  }
}
