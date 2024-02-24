package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class RandomCommand extends Command {

  private final Turtle myTurtle;
  private final Map<String, Double> myVariables;

  public RandomCommand(Turtle turtle, Map<String, Double> variables) {
    myTurtle = turtle;
    myVariables = variables;
  }

  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double rand = arguments.get(0).getValue();
    if (rand < 0) {
      throw new IllegalArgumentException("Max must be positive");
    }
    return Math.random() * rand;
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}
