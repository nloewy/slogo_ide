package slogo.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.model.api.Model;
import slogo.model.node.Node;

public class SlogoModel implements Model {

  // private ModelState modelState;
  private final Parser parser;

  private final SlogoListener myListener;
  private final List<Turtle> myTurtles;
  private final Map<String, Double> myVariables;
  private final ModelState modelstate;

  public SlogoModel(SlogoListener listener) {
    modelstate = new ModelState();
    myTurtles = new ArrayList<>();
    myVariables = new HashMap<>();

    parser = new Parser();
    myListener = listener;
  }

  public void parse(String commandStr) {
    List<Node> nodes = parser.parse(commandStr);
    for (Node node : nodes) {
      //execute, send to listener
    }
  }

  @Override
  public File loadXml(String path) {
    return null;
  }

  @Override
  public File saveXml(String path) {
    return null;
  }

  @Override
  public void resetModel() {
  }


}

