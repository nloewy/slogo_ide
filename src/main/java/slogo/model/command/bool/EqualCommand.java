package slogo.model.command.bool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The EqualCommand class represents the equality comparison operation. It evaluates two nodes and
 * returns 1.0 if they are equal, otherwise returns 0.0.
 *
 * @author Noah Loewy
 */
public class EqualCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private static final double TOLERANCE = .001;
  private final ModelState modelState;


  /**
   * Constructs an instance of EqualCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public EqualCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;

  }

  /**
   * Executes the equality comparison operation on the provided nodes.
   *
   * @param arguments a list of nodes representing values to be compared
   * @param turtleId  the id of the turtle currently active
   * @return 1.0 if the values are equal, otherwise returns 0.0
   */
  @Override
  public double execute(List<Node> arguments, int turtleId)
  {
    modelState.setOuter(false);
    return (Math.abs(arguments.get(0).evaluate() - arguments.get(1).evaluate()) <= TOLERANCE) ? 1.0
        : 0.0;
  }
}

