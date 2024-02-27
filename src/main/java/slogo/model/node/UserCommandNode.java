package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.SlogoListener;

public class UserCommandNode extends Node {

  private final String myToken;
  private int numArgs;
  private ModelState modelState;

  private SlogoListener myListener;
  public UserCommandNode(String token, ModelState modelState, SlogoListener listener) {
    super();
    myToken = token;
    numArgs = modelState.getUserDefinedCommands().get(token);
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
        // Replace the node with a ConstantNode
        int index = children.indexOf(child);
        if (index != -1) {
          ConstantNode constantNode = new ConstantNode(String.valueOf(value), modelState, myListener);
          children.set(index, constantNode);
        }
      } else {
        // Recursively check the children of the current node
        replaceNodesWithTokenHelper(child, tokenToReplace, value);
      }
    }
  }

  @Override
  public double getValue() throws InvocationTargetException, IllegalAccessException {

    List<Node> children = getChildren();
    System.out.println(children);
    for(int i = 0; i < numArgs - 2; i++) {
      Node rootOfSubtree = children.get(0);
      String tokenToReplace = children.get(1).getChildren().get(i).getToken();
      double value = children.get(i + 2).getValue();
      replaceNodesWithToken(rootOfSubtree, tokenToReplace, value);
    }
   // System.out.println(children.get(0));
    System.out.println(3);
    return children.get(0).getValue();
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
