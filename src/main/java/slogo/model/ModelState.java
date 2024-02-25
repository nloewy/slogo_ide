package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import slogo.model.api.ModelListener;

public class ModelState implements ModelListener {

  private final List<Turtle> myTurtles;
  private final Map<String, Double> myVariables;

  public ModelState() {
    myTurtles = new ArrayList<>();
    myVariables = new HashMap<>();
  }

  public List<Turtle> getTurtles() {
    return myTurtles;
  }

  public Map<String, Double> getVariables() {
    return myVariables;
  }

  @Override
  public double applyCommandToModelState(Function<ModelState, Double> action) {
    return action.apply(this);
  }

}
