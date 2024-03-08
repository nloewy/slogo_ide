package slogo.model.command.multiple;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The TellCommand class represents a command that given a turtle id(s), marks them as active.
 *
 * @author Noah Loewy
 */
public class TellCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;
  private static final int TELL_IDS_INDEX = 0;
  private final SlogoListener myListener;
  private final ModelState modelState;
  private final TurtleInformer turtleInformer;

  /**
   * Constructs an instance of TellCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public TellCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    myListener = listener;
    turtleInformer = new TurtleInformer();

  }

  /**
   * Executes the command to activate turtles with specified IDs, creating new turtles if needed.
   *
   * @param arguments a list of nodes representing the arguments passed to the command
   * @param turtle    the id of the turtle currently active
   * @return the ID of the last activated turtle
   */

  public double execute(List<Node> arguments, Turtle turtle) {
    List<Integer> toldTurtles = turtleInformer.informTurtles(arguments.get(TELL_IDS_INDEX),
        modelState, myListener);
    modelState.getActiveTurtles().clear();
    modelState.getActiveTurtles().add(toldTurtles);
    myListener.onSetActiveTurtles(modelState.getActiveTurtles().peek());
    return toldTurtles.get(toldTurtles.size() - 1);
  }

}


