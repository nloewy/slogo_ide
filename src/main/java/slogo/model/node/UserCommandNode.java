package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.model.ModelState;

public class UserCommandNode extends Node {

  private final String myToken;
  private final int numArgs;
  private final ModelState modelState;


  public UserCommandNode(String token, ModelState modelState) {
    super();
    myToken = token;
    numArgs = modelState.getUserDefinedCommands().get(token) - 2;
    this.modelState = modelState;
  }

  private void replaceNodesWithToken(Node node, String tokenToReplace, Node constantNode) {
    for (Node child : node.getChildren()) {
      replaceNodesWithTokenHelper(child, tokenToReplace, constantNode);
    }
  }




  private void replaceNodesWithTokenHelper(Node node, String tokenToReplace, Node constantNode) {
    List<Node> children = node.getChildren(); // Get the children of the current node
    for (Node child : children) {
      if (child.getToken().equals(tokenToReplace)) {
        int index = children.indexOf(child);
        if (index != -1) {
          children.set(index, constantNode);
        }
      } else {
        replaceNodesWithTokenHelper(child, tokenToReplace, constantNode);
      }
    }
  }
  private void replaceTokensWithNodes(Node node, Map<Node, String> nodeToVar) {
    for (Node child : node.getChildren()) {
      replaceTokensWithNodesHelper(child, nodeToVar);
    }
  }

  private void replaceTokensWithNodesHelper(Node node, Map<Node, String> nodeToVar) {
    List<Node> children = node.getChildren(); // Get the children of the current node
    for (Node child : children) {
      if (nodeToVar.containsKey(child)) {
        int index = children.indexOf(child);
        if (index != -1) {
          children.set(index, new VariableNode(nodeToVar.get(child), modelState));
          children.get(index).addListener(getListener());
        }
      } else {
        replaceTokensWithNodesHelper(child, nodeToVar);
      }
    }
  }

  @Override
  public double evaluate() throws InvocationTargetException, IllegalAccessException {
    List<Node> children = modelState.getUserDefinedCommandNodes().get(myToken);
    children.addAll(getChildren());
    Node rootOfSubtree = children.get(1);
    Map<Node, String> constantNodeToVariable = new HashMap<>();
    for (int i = 0; i < numArgs; i++) {
      String tokenToReplace = children.get(0).getChildren().get(i).getToken();
      double value = children.get(i + 2).evaluate();
      Node constantNode = new ConstantNode(String.valueOf(value), modelState);
      constantNodeToVariable.put(constantNode, tokenToReplace);
      replaceNodesWithToken(rootOfSubtree, tokenToReplace, constantNode);
    }
    double val = rootOfSubtree.evaluate();
    replaceTokensWithNodes(rootOfSubtree, constantNodeToVariable);
    modelState.getUserDefinedCommandNodes().put(myToken, children.subList(0,2));
    return val;
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
