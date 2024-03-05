package slogo.model.command.turtle;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.node.Node;

/**
 * The ClearScreenCommand class clears the screen and returns the turtle to its initial position. It
 * inherits from the HomeCommand class and resets the turtle's state to its initial state.
 * Additionally, it notifies the listener to reset the turtle's state.
 *
 * @author Noah Loewy
 */
public class ClearScreenCommand extends HomeCommand {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 0;
  private final ModelState modelState;
  private final SlogoListener listener;

  /**
   * Constructs a ClearScreenCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public ClearScreenCommand(ModelState modelState, SlogoListener listener) {
    super(modelState, listener);
    this.modelState = modelState;
    this.listener = listener;
  }

  /**
   * Executes the ClearScreenCommand by calling the HomeCommand class's execute method to return the
   * turtle to its initial position and then notifies the listener to reset the turtle's state.
   *
   * @param arguments a list of nodes representing the arguments of the command (none for this
   *                  command)
   * @param index the index of the turtle in the list at the top of getActiveTurtles() stack

   * @return the result of executing the super class's execute method
   */
  @Override
  public double execute(List<Node> arguments, int index) {
    double ret = super.execute(arguments, index);
    listener.onResetTurtle(modelState.getTurtles().get(modelState.getActiveTurtles().peek().get(index)).getImmutableTurtle().id());
    return ret;
  }
}
