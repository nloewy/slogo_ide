package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
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
  private final ModelState modelState;
  private final SlogoListener listener;

  /**
   * Constructs an instance of ShowTurtleCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public ShowTurtleCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  /**
   * Executes the command to show the turtle. Sets the visibility of the turtle to true.
   *
   * @param arguments a list containing nodes representing the arguments (none required)
   * @param index     the index of the turtle in the list at the top of getActiveTurtles() stack
   * @return 1.0 indicating successful execution
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */

  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    modelState.outer = false;
    Turtle turtle = modelState.getTurtles().get(index);
    turtle.setVisible(true);
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return 1.0;
  }
}


