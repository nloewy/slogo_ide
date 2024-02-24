package slogo.model.command.bool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class OrCommand extends Command {

  private final Turtle myTurtle;
  private final Map<String, Double> myVariables;

  public OrCommand(Turtle turtle, Map<String, Double> variables) {
    myTurtle = turtle;
    myVariables = variables;
  }

  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return (!(arguments.get(0).getValue() == 0) || !(arguments.get(1).getValue() == 0)) ? 1 : 0;

  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}
