package slogo.model.command.bool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class GreaterThanCommand implements Command {

  public static final int NUM_ARGS = 2;

  public GreaterThanCommand(ModelState modelState, SlogoListener listener) {
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    double arg2 = arguments.get(1).getValue();
    return (arg1 > arg2) ? 1.0 : 0.0;
  }

}