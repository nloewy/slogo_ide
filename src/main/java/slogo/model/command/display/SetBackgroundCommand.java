package slogo.model.command.display;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class SetBackgroundCommand implements Command {

  /** The SetBackgroundCommand represents a command that alters the Pen index of the
   * workspace. It returns the heading of the requested turtle in the model state.
   *
   * @author Noah Loewy
   */

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;

  private final ModelState modelState;

  /**
   * Constructs an instance of SetPenCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public SetBackgroundCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Updates the Background color of the workspace.
   *
   * @param arguments a list of nodes representing arguments
   * @param turtleId  the id of the turtle currently active
   * @return the background color of the workspace
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) {
    modelState.outer = false;
    double val = Math.round(arguments.get(0).evaluate());
    for (Turtle turtle : modelState.getTurtles().values()) {
      turtle.setBgColor((int) val);
    }
    return val;
  }
}

