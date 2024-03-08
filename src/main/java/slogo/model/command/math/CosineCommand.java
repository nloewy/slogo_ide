package slogo.model.command.math;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The CosineCommand class represents the cosine mathematical operation. It calculates the cosine of
 * a given angle in degrees.
 *
 * @author Noah Loewy
 */
public class CosineCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;


  /**
   * Constructs an instance of CosineCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public CosineCommand(ModelState modelState, SlogoListener listener) {
    //DO NOTHING
  }

  /**
   * Executes the cosine mathematical operation.
   *
   * @param arguments a list containing a single node representing the angle in degrees
   * @param turtle    the id of the turtle currently active
   * @return the cosine of the input angle
   */
  @Override
  public double execute(List<Node> arguments, Turtle turtle) {
    double arg1 = arguments.get(0).evaluate();
    return Math.cos(arg1 * Math.PI / 180);
  }
}
