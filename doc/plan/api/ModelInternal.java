/**
 * Represents a node in an abstract syntax tree.
 */
public abstract class Node {

  /**
   * Retrieves the value of the node.
   *
   * @return The value of the node.
   * @throws InvocationTargetException If the underlying method throws an exception.
   * @throws IllegalAccessException    If the underlying method is inaccessible.
   */
  public abstract double getValue() throws InvocationTargetException, IllegalAccessException;

  /**
   * Retrieves the children of the node.
   *
   * @return The children of the node.
   */
  public List<Node> getChildren() {
    return myChildren;
  }

  /**
   * Adds a child node to the current node.
   *
   * @param node The child node to be added.
   */
  public void addChild(Node node) {
    myChildren.add(node);
  }

  /**
   * Retrieves the token associated with the node.
   *
   * @return The token associated with the node.
   */
  public String getToken();
}


/**
 * Represents a command to be executed.
 */
public abstract class Command {

  /**
   * Executes the command with the given arguments.
   *
   * @param arguments The arguments for the command.
   * @return The result of executing the command.
   * @throws InvocationTargetException If the underlying method throws an exception.
   * @throws IllegalAccessException    If the underlying method is inaccessible.
   */
  public abstract Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException;

  /**
   * Retrieves the number of arguments expected by the command.
   *
   * @return The number of arguments expected by the command.
   */
  public abstract int getNumArgs();

  /**
   * Notifies the provided listener with a return value.
   *
   * @param listener The listener to be notified.
   * @param value    The value to be returned by a command.
   */
  public void notifyListener(SlogoListener listener, double value) {
    listener.onReturn(value);
  }
}


/**
 * Represents the state of the model (turtles, variables/commands defined by users).
 */
public class ModelState {

  /**
   * Retrieves the turtles in the model.
   *
   * @return The list of turtles in the model.
   */
  public List<Turtle> getTurtles();

  /**
   * Retrieves the variables currently defined in the Slogo model.
   *
   * @return The map of variables in the model, from String "names/tokens" to their numeric values.
   */
  public Map<String, Double> getVariables();

  /**
   * Retrieves the user-defined commands in the model.
   *
   * @return The map of user-defined commands in the model, from String command names, to list of
   * Nodes that can be executed.
   */
  public Map<String, List<Node>> getUserDefinedCommands();

}

/**
 * Represents a turtle in the model.
 */
public class Turtle {

  /**
   * Retrieves the y-coordinate of the turtle.
   *
   * @return The y-coordinate of the turtle.
   */
  public double getY();

  /**
   * Sets the y-coordinate of the turtle.
   *
   * @param myY The new y-coordinate of the turtle.
   */
  public void setY(double myY);

  /**
   * Retrieves the x-coordinate of the turtle.
   *
   * @return The x-coordinate of the turtle.
   */
  public double getX();

  /**
   * Sets the x-coordinate of the turtle.
   *
   * @param myX The new x-coordinate of the turtle.
   */
  public void setX(double myX);

  /**
   * Retrieves the heading of the turtle.
   *
   * @return The heading of the turtle.
   */
  public double getHeading();

  /**
   * Sets the heading of the turtle.
   *
   * @param myHeading The new heading of the turtle.
   */
  public void setHeading(double myHeading);

  /**
   * Retrieves an immutable record of the turtle.
   *
   * @return An immutable record of the turtle.
   */
  public TurtleRecord getImmutableTurtle();

  /**
   * Retrieves the pen state of the turtle.
   *
   * @return The pen state of the turtle.
   */
  public boolean getPen();

  /**
   * Sets the pen state of the turtle.
   *
   * @param b The new pen state of the turtle.
   */
  public void setPen(boolean b);

  /**
   * Retrieves the visibility state of the turtle.
   *
   * @return The visibility state of the turtle.
   */
  public boolean getVisible();

  /**
   * Sets the visibility state of the turtle.
   *
   * @param b The new visibility state of the turtle.
   */
  public void setVisible(boolean b);

  /**
   * Retrieves the ID of the turtle.
   *
   * @return The ID of the turtle.
   */
  public int getId();
}
