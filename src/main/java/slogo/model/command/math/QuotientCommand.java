package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class QuotientCommand implements Command {

  public static final int NUM_ARGS = 2;

  public QuotientCommand(ModelState modelState, SlogoListener listener) {
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    if (arguments.get(1).evaluate() == 0) {
      throw new InvalidOperandException("Divisor must be non-zero");
    }
    return arguments.get(0).evaluate() / arguments.get(1).evaluate();
  }
}
