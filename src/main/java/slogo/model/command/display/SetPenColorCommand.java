package slogo.model.command.display;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class SetPenColorCommand implements Command {

  /** The SetPenCommand represents a command that alters the Pen index of the
   * workspace. It returns the heading of the requested turtle in the model state.
   *
   * @author Noah Loewy
   */

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;

  private final ModelState modelState;
  private final SlogoListener myListener;

  /**
   * Constructs an instance of SetPenCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public SetPenColorCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    myListener = listener;
  }

  /**
   * Updates the Pen color of the workspace.
   *
   * @param arguments a list of nodes representing arguments
   * @param turtleId  the id of the turtle currently active
   * @return the heading of the active turtle
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) {
    modelState.outer = false;
    Turtle turtle = modelState.getTurtles().get(turtleId);
    turtle.setPenColor((int) Math.round(arguments.get(0).evaluate()));
    myListener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return modelState.getTurtles().get(turtleId).getPenColor();
  }
}

