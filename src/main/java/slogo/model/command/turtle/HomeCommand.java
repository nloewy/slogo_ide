package slogo.model.command.turtle;

import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The HomeCommand class represents the command to move the turtle to its home position (0, 0). It
 * sets the turtle's position to the origin and notifies the listener of the state change. The
 * distance moved by the turtle from its previous position to the home position is returned.
 *
 * @author Noah Loewy
 */
public class HomeCommand implements Command {

  /**
   * The number of arguments this command expects.
   */
  public static final int NUM_ARGS = 0;

  private final ModelState modelState;
  private final SlogoListener listener;

  /**
   * Constructs a HomeCommand with the given model state and listener.
   *
   * @param modelState the model state containing information about the turtle
   * @param listener   the listener for state change events
   */
  public HomeCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  /**
   * Executes the HomeCommand, moving the turtle to its home position.
   *
   * @param arguments a list of nodes representing the arguments for this command (none expected)
   * @param index the index of the turtle in the list at the top of getActiveTurtles() stack

   * @return the distance moved by the turtle from its previous position to the home position
   */
  @Override
  public double execute(List<Node> arguments, int index) {
    modelState.outer = false;
    Turtle turtle = modelState.getTurtles().get(index);
    double currentX = turtle.getX();
    double currentY = turtle.getY();
    turtle.setX(0);
    turtle.setY(0);
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return MathUtils.dist(0, 0, currentX, currentY);
  }
}
