package slogo.model.command.multiple;

import java.util.ArrayList;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The TurtleInformer class provides utility methods for informing turtles.
 *
 * @author Noah Loewy
 */
public abstract class InformCommand implements Command {

  public abstract double execute(List<Node> arguments, Turtle turtle);
  /**
   * Informs turtles based on the provided node containing turtle IDs.
   *
   * @param ids        the node containing turtle IDs
   * @param modelState the model state
   * @param myListener the listener for state change events
   * @return the list of informed turtle IDs
   */

  protected List<Integer> informTurtles(Node ids, ModelState modelState, SlogoListener myListener) {
    List<Integer> tempList = new ArrayList<>();
    if (ids.getChildren().isEmpty()) { // case where singular turtle is informed
      addIdToList((int) Math.round(ids.evaluate()), tempList, modelState, myListener);
    } else {
      addIdsToList(ids, tempList, modelState,
          myListener); // case where multiple turtles are informed
    }
    return tempList;
  }


  private void addIdsToList(Node ids, List<Integer> tempList, ModelState modelState,
      SlogoListener myListener) {
    for (Node node : ids.getChildren()) {
      if (!node.getToken().equals("]")) {
        addIdToList((int) Math.round(node.evaluate()), tempList, modelState, myListener);
      }
    }
  }

  private void addIdToList(int id, List<Integer> tempList, ModelState modelState,
      SlogoListener myListener) {
    if (!modelState.getTurtles().containsKey(id)) {
      modelState.getTurtles().put(id, new Turtle(id));
      myListener.onUpdateTurtleState(modelState.getTurtles().get(id).getImmutableTurtle());
    }
    tempList.add(id);
  }

}
