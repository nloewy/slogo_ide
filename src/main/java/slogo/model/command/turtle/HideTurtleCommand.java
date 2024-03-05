package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The HideTurtleCommand class represents the command to hide the turtle. It sets the visibility of
 * the turtle to false. Hiding the turtle means it will not be displayed on the screen. This command
 * does not take any arguments.
 *
 * @author Noah Loewy
 */
public class HideTurtleCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 0;

  private final ModelState modelState;
  private final SlogoListener listener;

  /**
   * Constructs an instance of HideTurtleCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public HideTurtleCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  /**
   * Executes the command to hide the turtle. Sets the visibility of the turtle to false.
   *
   * @param arguments a list containing nodes representing the arguments (none required)
   * @param index the index of the turtle in the list at the top of getActiveTurtles() stack

   * @return 0.0 indicating successful execution
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    Turtle turtle = modelState.getTurtles().get(modelState.getActiveTurtles().peek().get(index));
    turtle.setVisible(false);
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());    return 0.0;
  }
}
