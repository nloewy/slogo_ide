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
 * The AskCommand class represents a command that given a turtle id(s), marks them as active for a
 * sequence of commands, then goes back to the previously active turtles
 *
 * @author Noah Loewy
 */
public class AskCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;

  private final SlogoListener myListener;
  private final ModelState modelState;

  /**
   * Constructs an instance of AskCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public AskCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    myListener = listener;
  }

  /**
   * Executes the command to activate turtles with specified IDs, creating new turtles if needed,
   * runs the given commands, and then goes back to previously active turtle.
   *
   * @param arguments a list of nodes representing the arguments passed to the command
   * @param turtleId     the id of the turtle currently active
   * @return result of last command run by the last turtle.
   * @throws InvocationTargetException if an error occurs during command execution
   * @throws IllegalAccessException    if access to the method or field is denied
   */

  @Override
  public double execute(List<Node> arguments, int turtleId) {
    List<Integer> tempList = new ArrayList<>();
    for (Node node : arguments.get(0).getChildren()) {
      modelState.outer = false;

      int id = (int) Math.round(node.evaluate());
      if (!modelState.getTurtles().containsKey(id)) {
        modelState.getTurtles().put(id, new Turtle(id));
        myListener.onResetTurtle(id);
      }
      tempList.add(id);
    }
    modelState.getActiveTurtles().add(tempList);
    myListener.onSetActiveTurtles(modelState.getActiveTurtles().peek());
    double val = 0.0;
    for (Node node : arguments.get(1).getChildren()) {
      for (int i : tempList) {
        modelState.outer = false;
        modelState.currTurtle = i;
        val = node.evaluate();
      }
    }

    modelState.getActiveTurtles().pop();
    modelState.outer = false;
    return val;

  }
}

