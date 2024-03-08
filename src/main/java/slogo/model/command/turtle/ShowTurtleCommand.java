package slogo.model.command.turtle;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The ShowTurtleCommand class represents the command to show the turtle. It sets the visibility of
 * the turtle to true. Showing the turtle means it will be displayed on the screen. This command
 * does not take any arguments.
 *
 * @author Noah Loewy
 */

public class ShowTurtleCommand implements Command {

  /**
   * The number of arguments this command requires.
   */

  public static final int NUM_ARGS = 0;
  private final SlogoListener listener;

  /**
   * Constructs an instance of ShowTurtleCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public ShowTurtleCommand(ModelState modelState, SlogoListener listener) {
    this.listener = listener;
  }

  /**
   * Executes the command to show the turtle. Sets the visibility of the turtle to true.
   *
   * @param arguments a list containing nodes representing the arguments (none required)
   * @param turtle    the id of the turtle currently active
   * @return 1.0 indicating successful execution
   */

  @Override
  public double execute(List<Node> arguments, Turtle turtle) {
    turtle.setVisible(true);
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return 1.0;
  }
}


