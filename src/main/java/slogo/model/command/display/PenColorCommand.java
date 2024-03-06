package slogo.model.command.display;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The PenColorCommand represents a command that retrieves the pen color of the active turtle
 *
 * @author Noah Loewy
 */
public class PenColorCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 0;

  private final ModelState modelState;

  /**
   * Constructs an instance of PenColorCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public PenColorCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Retrieves the pen color of the requested turtle.
   *
   * @param arguments a list of nodes representing arguments (not used in this command)
   * @param turtleId  the id of the turtle currently active
   * @return the pen color of the requested turtle
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) {
    modelState.setOuter(false);
    return modelState.getTurtles().get(turtleId).getPenColor();
  }
}
