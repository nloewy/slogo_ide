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
   * Executes the command to activate turtles with specified IDs, creating new turtles if needed.
   *
   * @param arguments a list of nodes representing the arguments passed to the command
   * @param index     the index of the command in the list of commands
   * @return the ID of the last activated turtle
   * @throws InvocationTargetException if an error occurs during command execution
   * @throws IllegalAccessException    if access to the method or field is denied
   */

  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    List<Integer> tempList = new ArrayList<>();
    int id = 0;
    if(arguments.get(0).getChildren().isEmpty()) {
      ;
      id = (int) Math.round(arguments.get(0).evaluate());
      if (!modelState.getTurtles().containsKey(id)) {
        modelState.getTurtles().put(id, new Turtle(id));
        myListener.onUpdateTurtleState(modelState.getTurtles().get(id).getImmutableTurtle());
      }
      tempList.add(id);
    }
    else {
      for (Node node : arguments.get(0).getChildren()) {
        if(node.getToken().equals("]")) {continue;}

        id = (int) Math.round(node.evaluate());
        if (!modelState.getTurtles().containsKey(id)) {
          modelState.getTurtles().put(id, new Turtle(id));
          myListener.onUpdateTurtleState(modelState.getTurtles().get(id).getImmutableTurtle());
        }
        tempList.add(id);
      }
    }
    modelState.getActiveTurtles().clear();
    modelState.getActiveTurtles().add(tempList);
    myListener.onSetActiveTurtles(modelState.getActiveTurtles().peek());
    return id;
  }
}

