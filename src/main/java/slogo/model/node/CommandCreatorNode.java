package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;

/**
 * The CommandCreatorNode class represents a node that creates a user-defined command in Slogo
 *
 * @author Noah Loewy
 */


public class CommandCreatorNode extends Node {

  private final String myToken;
  private final ModelState myModelState;
  private final int myNumArgs;

  /**
   * Constructs a new CommandCreatorNode with the given token, model state, and number of arguments.
   * Unlike other implementations of Node, CommandCreatorNode takes an extra parameter for the
   * number of arguments, which breaks the abstraction. However, this special case was necessary to
   * support user-defined commands properly. Adds the new user defined command to the map in model
   * state from user-defined command names to the number of arguments they take
   *
   * @param token     the token representing the command
   * @param modelState the model state of the application
   * @param numArgs   the number of arguments of the user-defined command to be created
   */

  public CommandCreatorNode(String token, ModelState modelState, int numArgs) {
    super();
    myModelState = modelState;
    myToken = token;
    myNumArgs = numArgs;
    myModelState.getUserDefinedCommands().put(myToken, myNumArgs);
  }

  /**
   * Adds the children of the node (the variables and instructions for executing the User Defined
   * Command) to the map from user defined command names to nodes. Also provides the listener with
   * a string representation of the command definition
   *
   * @return 1.0, indicating command successfully declined
   * @throws InvocationTargetException if an error occurs during invocation
   * @throws IllegalAccessException    if access to the method is denied
   */


  @Override
  public double evaluate() throws InvocationTargetException, IllegalAccessException {
    List<Node> children = getChildren();
    myModelState.getUserDefinedCommandNodes().put(myToken, children);
    getListener().onUserDefinedCommand(toString());
    return 1.0;
  }

  /**
   * Gets the number of arguments required by this node.
   * Always returns 2, with the first argument a list node of variables, and the second argument a
   * list node of commands
   *
   * @return the number of arguments
   */

  @Override
  public int getNumArgs() {
    return 2;
  }


  /**
   * Gets the token representing the command.
   *
   * @return the token representing the command, with "to" preceeding it for the sake of toString
   */

  public String getToken() {
    return "to " + myToken;
  }

  /**
   * Returns a string representation of the command creator node and its children. Different from
   * generic Node toString as the command name and its arguments are placed on the first line
   *
   * @return a string representation of the node
   */

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getToken());
    if (!getChildren().isEmpty()) {
      sb.append("\t").append(getChildren().get(0).getToken());
      for (int i = 0; i < getChildren().get(0).getChildren().size(); i++) {
        sb.append("\t").append(getChildren().get(0).getChildren().get(i).getToken());
      }
      sb.append(toStringHelper(this, 0, true));

    }
    return sb.toString();
  }

  /**
   * Helper method to recursively build the string representation of the command creator node.
   *
   * @param node       the current node
   * @param indent     the indentation level
   * @param firstLevel flag indicating whether it's the first level of recursion (for the variables
   *                   to stay on the same line as the command name)
   * @return the string representation of the node and its children
   */

  private String toStringHelper(Node node, int indent, boolean firstLevel) {
    StringBuilder sb = new StringBuilder();
    if (!firstLevel) {
      sb.append("\n");
      sb.append("\t".repeat(indent)).append(node.getToken());
    }
    for (int i = 0; i < node.getChildren().size(); i++) {
      if (firstLevel && i == 0) {
        continue;
      }
      sb.append(toStringHelper(node.getChildren().get(i), indent + 1, false));
    }
    return sb.toString();
  }
}