package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.exceptions.InvalidOperandException;
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
  }

  /**
   * Executes the tangent mathematical operation.
   *
   * @param arguments a list containing a single node representing the angle in degrees
   * @return the tangent of the input angle
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   * @throws InvalidOperandException   if the tangent function is undefined
   */
  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException, InvalidOperandException {
    double arg1 = arguments.get(0).evaluate();
    if (Math.abs(arg1 % 180) == 90) {
      throw new InvalidOperandException("Illegal Value for Tangent Function");
    }
    return Math.tan(Math.toRadians(arg1));
  }
}
