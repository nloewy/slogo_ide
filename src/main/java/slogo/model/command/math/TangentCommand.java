package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import slogo.mathutils.MathUtils;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class TangentCommand extends Command {

  private final Turtle myTurtle;
  private final Map<String, Double> myVariables;

  public TangentCommand(Turtle turtle, Map<String, Double> variables) {
    myTurtle = turtle;
    myVariables = variables;
  }

  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double angle = arguments.get(0).getValue();
    if (Math.abs(angle % 180) == 90) {
      throw new ArithmeticException("Illegal Value for Tangent Function");
    }
    return Math.tan(MathUtils.toRadians(angle));
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}
