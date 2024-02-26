package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.model.node.Node;

public class ModelState {

  private final List<Turtle> myTurtles;
  private final Map<String, Double> myVariables;
  private final Map<String, List<Node>> myUserDefinedCommands;

  public ModelState() {
    myTurtles = new ArrayList<>();
    myVariables = new HashMap<>();
    myUserDefinedCommands = new HashMap<>();
  }

  public List<Turtle> getTurtles() {
    return myTurtles;
  }

  public Map<String, Double> getVariables() {
    return myVariables;
  }

  public Map<String, List<Node>> getUserDefinedCommands() {
    return myUserDefinedCommands;
  }
}
