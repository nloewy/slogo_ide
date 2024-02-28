package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;

public class UserCommandNode extends Node {

  private final String myToken;
  private final int numArgs;
  private final ModelState modelState;

  private final SlogoListener myListener;

  public UserCommandNode(String token, ModelState modelState, SlogoListener listener) {
    super();
    myToken = token;
    numArgs = modelState.getUserDefinedCommands().get(token) - 2;
    this.modelState = modelState;
    myListener = listener;
  }

  private void replaceNodesWithToken(Node node, String tokenToReplace, double value) {
    for (Node child : node.getChildren()) {
      replaceNodesWithTokenHelper(child, tokenToReplace, value);
    }
  }

  private void replaceNodesWithTokenHelper(Node node, String tokenToReplace, double value) {
    List<Node> children = node.getChildren(); // Get the children of the current node
    for (Node child : children) {
      if (child.getToken().equals(tokenToReplace)) {
        int index = children.indexOf(child);
        if (index != -1) {
          ConstantNode constantNode = new ConstantNode(String.valueOf(value), modelState,
              myListener);
          children.set(index, constantNode);
        }
      } else {replaceNodesWithTokenHelper(child, tokenToReplace, value);
      }
    }
  }

  @Override
  public double getValue() throws InvocationTargetException, IllegalAccessException {
    List<Node> children = modelState.getUserDefinedCommandNodes().get(myToken);
    children.addAll(getChildren());
    for (int i = 0; i < numArgs; i++) {
      Node rootOfSubtree = children.get(1);
      String tokenToReplace = children.get(0).getChildren().get(i).getToken();
      double value = children.get(i + 2).getValue();
      replaceNodesWithToken(rootOfSubtree, tokenToReplace, value);
    }
    return children.get(1).getValue();
  }

  @Override
  public int getNumArgs() {
    return numArgs;
  }

  @Override
  public String getToken() {
    return myToken;
  }
}
