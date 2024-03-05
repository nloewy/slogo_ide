package slogo.model.command.multiple;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The TellCommand class represents a command that given a turtle id(s), marks them as active
 *
 * @author Noah Loewy
 */
public class TellCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;

  private final SlogoListener myListener;
  private final ModelState modelState;

  /**
   * Constructs an instance of TellCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public TellCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    myListener = listener;

  }

  /**
   * Retrieves the id of active turtle in the current workspace.
   *
   * @param arguments a list of nodes representing arguments (not used in this command)
   * @param index the index of the turtle in the list at the top of getActiveTurtles() stack
   * @return the id of currently active turtle
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */

  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    modelState.getActiveTurtles().clear();
    modelState.getActiveTurtles().add(new ArrayList<>());
    int id = 0;
    for(Node node : arguments.get(0).getChildren()) {
      id = (int) Math.round(node.evaluate());
      if(!modelState.getTurtles().containsKey(id)) {
        modelState.getTurtles().put(id, new Turtle(id));
        myListener.onResetTurtle(id);
      }
      modelState.getActiveTurtles().peek().add(id);
    }
    myListener.onSetActiveTurtles(modelState.getActiveTurtles().peek());
    return id;
  }
}

