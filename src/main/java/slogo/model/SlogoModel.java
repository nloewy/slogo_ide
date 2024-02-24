package slogo.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.model.api.Model;

public class SlogoModel implements Model {

 // private ModelState modelState;
  private Parser parser;

  private SlogoListener myListener;
  private List<Turtle> myTurtles;
  private Map<String, Double> myVariables;
  private ModelState modelstate;

  public SlogoModel(SlogoListener listener) {
    modelstate = new ModelState();
    myTurtles = new ArrayList<>();
    myVariables = new HashMap<>();

    parser = new Parser();
    myListener = listener;
  }

  public void parse(String commandStr) {
    List<Node> nodes = parser.parse(commandStr);
    for(Node node : nodes) {
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

