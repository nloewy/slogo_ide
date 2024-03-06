package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import slogo.model.api.SlogoListener;
import slogo.model.exceptions.IncompleteClassException;

/**
 * The abstract class Node represents a node in the syntax tree of a Slogo expression. It provides
 * common functionality for different types of nodes.
 *
 * @author Noah Loewy
 */

public abstract class Node {

  private List<Node> myChildren = new ArrayList<>();
  private SlogoListener myListener;


  public abstract double evaluate();

  /**
   * Retrieves myChildren instance variable of the node, which is later passed in as an argument to
   * the evaluate function
   *
   * @return the list of children nodes
   */
  public List<Node> getChildren() {
    return myChildren;
  }

  /**
   * Adds a child node to this node.
   *
   * @param node the child node to add
   */

  public void addChild(Node node) {
    myChildren.add(node);
  }

  /**
   * Abstract method to get the token of the node.
   *
   * @return the token of the node
   */

  public abstract String getToken();

  /**
   * Gets the number of arguments required by this node for the execute function.
   *
   * @return the number of arguments
   * @throws IncompleteClassException if the class is incomplete
   */

  public int getNumArgs() throws IncompleteClassException {
    return 0;
  }

  /**
   * Returns a string representation of the node and its children.
   *
   * @return a string representation of the node
   */

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Node child : getChildren()) {
      sb.append(toStringHelper(child, 0));
    }
    return sb.toString();
  }

  /**
   * Sets the listener for the node.
   *
   * @param myListener the listener to set
   */

  public void addListener(SlogoListener myListener) {
    this.myListener = myListener;
  }

  /**
   * Gets the listener for the node.
   *
   * @return the listener for the node
   */

  protected SlogoListener getListener() {
    return myListener;
  }

  /**
   * Helper method to recursively build the string representation of the node.
   *
   * @param node   the current node
   * @param indent the indentation level (level of nesting in command)
   * @return the string representation of the node and its children
   */

  private String toStringHelper(Node node, int indent) {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    sb.append(" ".repeat(indent)).append(node.getToken());
    for (int i = 0; i < node.getChildren().size(); i++) {
      sb.append(toStringHelper(node.getChildren().get(i), indent + 1));
    }
    return sb.toString();
  }

  /**
   * Removes all children nodes from this node.
   */
  public void removeChildren() {
    myChildren = new ArrayList<>();
  }
}


