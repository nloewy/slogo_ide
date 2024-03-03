package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class RandomRangeCommand implements Command {

  public static final int NUM_ARGS = 2;

  public RandomRangeCommand(ModelState modelState, SlogoListener listener) {
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).evaluate();
    double arg2 = arguments.get(1).evaluate();
    if (arg1 > arg2) {
      throw new InvalidOperandException("Min must be less than Max");
    }
    return arg1 + (arg2 - arg1) * Math.random();
  }
}
