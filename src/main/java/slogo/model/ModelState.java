package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.model.node.Node;


/**
 * The ModelState class represents the state of the Slogo simulation model, including the collection
 * of turtles, variables, and user-defined commands. It maintains information about the current
 * state of the simulation environment, allowing for the manipulation and retrieval of various
 * properties and data.
 * <p>
 * This class serves as a container for storing the state of the Slogo model. It encapsulates the
 * lists and maps that hold information about turtles, variables, and user-defined commands. By
 * providing accessors for these data structures, it enables other components of the application,
 * such as the commands, to interact with and modify the model state as needed.
 *
 * @author Noah Loewy
 */
public class ModelState {

  /**
  A list of Turtle objects representing the turtles currently present in the simulation.
   */
  private final List<Turtle> myTurtles;
  /**
   A map from variable names (lowercase) to the current value of the variable.
   */
  private final Map<String, Double> myVariables;
  /**
   * A map from names of user defined commands to the number of arguments a command expects
   */
  private final Map<String, Integer> myUserDefinedCommands;
  /**
   * A map from names of user defined commands to the list of command nodes that are executed to
   * obtain the nodes needed to execute the user defined commands
   */
  private final Map<String, List<Node>> myUserDefinedCommandNodes;

  /**
   * Initializes turtles, variables, commands to default values (empty lists / maps)
   */
  public ModelState() {
    myTurtles = new ArrayList<>();
    myVariables = new HashMap<>();
    myUserDefinedCommands = new HashMap<>();
    myUserDefinedCommandNodes = new HashMap<>();
  }

  /**
   * Returns the list of turtles currently present in the Slogo simulation model.
   *
   * @return a list of Turtle objects representing the turtles in the simulation
   */
  public List<Turtle> getTurtles() {
    return myTurtles;
  }

  /**
   * Returns the map of variables defined in the Slogo simulation model.
   *
   * @return a map of variable names to their corresponding values
   */
  public Map<String, Double> getVariables() {
    return myVariables;
  }

  /**
   * Returns the map of user-defined commands registered in the Slogo simulation model.
   *
   * @return a map of user-defined command names to the number of arguments they expect
   */
  public Map<String, Integer> getUserDefinedCommands() {
    return myUserDefinedCommands;
  }

  /**
   * Returns the map of user-defined command syntax trees stored in the Slogo simulation model.
   *
   * @return a map of user-defined command names to their parsed syntax trees
   */
  public Map<String, List<Node>> getUserDefinedCommandNodes() {
    return myUserDefinedCommandNodes;
  }
}
