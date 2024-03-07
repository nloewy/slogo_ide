package slogo.model.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.model.ModelState;
import slogo.model.exceptions.InsufficientArgumentsException;

/**
 * Represents a user-defined command node in the syntax tree. This node evaluates a user-defined
 * command by replacing its parameters with their corresponding values, then evaluates the resulting
 * subtree.
 *
 * @author Noah Loewy
 */

public class UserCommandNode extends Node {

  private final String myToken;
  private final int numArgs;
  private final ModelState modelState;


  /**
   * Constructs a UserCommandNode with the specified token and model state.
   *
   * @param token      the token representing the user-defined command node (name of command)
   * @param modelState the model state associated with this node
   */

  public UserCommandNode(String token, ModelState modelState) {
    super();
    myToken = token;
    numArgs = modelState.getUserDefinedCommands().get(token) - 2;
    this.modelState = modelState;
  }

  /**
   * Evaluates the user-defined command node. It calls helper methods to replace the parameters with
   * their corresponding values, evaluates the children nodes, and updates the model state with the
   * evaluated command.
   *
   * @return the result of evaluating the user-defined command
   */
  @Override
  public double evaluate() {
    if (modelState.getOuter()) {
      return evaluateOuter();
    } else {
      return evaluateInner();
    }
  }

  private double evaluateOuter() {
    double val = 0;
    for (int index = 0; index < modelState.getActiveTurtles().peek().size(); index++) {
      val = evaluateHelper(true, index);
    }
    modelState.setOuter(true);
    return val;
  }

  private double evaluateInner() {
    return evaluateHelper(false, 0);
  }

  private double evaluateHelper(boolean isOuter, int index) {
    List<Node> children = modelState.getUserDefinedCommandNodes().get(myToken);
    children.addAll(getChildren());
    Node rootOfSubtree = children.get(1);
    if (children.size() < numArgs + 2) {
      throw new InsufficientArgumentsException("", myToken);
    }
    Map<Node, String> constantNodeToVariable = new HashMap<>();
    for (int i = 0; i < numArgs; i++) {
      String tokenToReplace = children.get(0).getChildren().get(i).getToken();
      modelState.setOuter(false);
      double value = children.get(i + 2).evaluate();
      Node constantNode = new ConstantNode(String.valueOf(value), modelState);
      constantNodeToVariable.put(constantNode, tokenToReplace);
      replaceNodesWithToken(rootOfSubtree, tokenToReplace, constantNode);
    }
    modelState.setOuter(false);
    if (isOuter) {
      modelState.setCurrTurtle(modelState.getActiveTurtles().peek().get(index));
    }
    double val = rootOfSubtree.evaluate();
    replaceTokensWithNodes(rootOfSubtree, constantNodeToVariable);
    modelState.getUserDefinedCommandNodes().put(myToken, children.subList(0, 2));
    return val;
  }




  /**
   * Retrieves the number of arguments expected by the user-defined command.
   *
   * @return the number of arguments expected by the user-defined command, which is obtained from
   * the map in model state from command names to arguments expected
   */

  @Override
  public int getNumArgs() {
    return numArgs;
  }

  /**
   * Retrieves the token associated with this user-defined command node.
   *
   * @return the token representing the name of the user-defined command node
   */

  @Override
  public String getToken() {
    return myToken;
  }

  /**
   * Replaces nodes in the subtree rooted at the given node with a constant node.
   *
   * @param node           The root node of the subtree to search.
   * @param tokenToReplace The token to search for and replace.
   * @param constantNode   The constant node to replace the matching tokens with.
   */
  private void replaceNodesWithToken(Node node, String tokenToReplace, Node constantNode) {
    for (Node child : node.getChildren()) {
      replaceNodesWithTokenHelper(child, tokenToReplace, constantNode);
    }
  }

  /**
   * Helper method for replacing nodes in the subtree with a constant node. Recursively searches
   * through the subtree and replaces matching tokens with the constant node.
   *
   * @param node           The current node being examined.
   * @param tokenToReplace The token to search for and replace.
   * @param constantNode   The constant node to replace the matching tokens with.
   */
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

  /**
   * Replaces tokens in the subtree rooted at the given node with variable nodes.
   *
   * @param node      The root node of the subtree to search.
   * @param nodeToVar A mapping of nodes to their corresponding variable tokens.
   */
  private void replaceTokensWithNodes(Node node, Map<Node, String> nodeToVar) {
    for (Node child : node.getChildren()) {
      replaceTokensWithNodesHelper(child, nodeToVar);
    }
  }

  /**
   * Helper method for replacing tokens with variable nodes in the subtree. Recursively searches
   * through the subtree and replaces matching tokens with variable nodes.
   *
   * @param node      The current node being examined.
   * @param nodeToVar A mapping of nodes to their corresponding variable tokens.
   */
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

}
