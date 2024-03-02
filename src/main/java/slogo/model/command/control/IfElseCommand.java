package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class IfElseCommand implements Command {

  public static final int NUM_ARGS = 3;

  public IfElseCommand(ModelState modelState, SlogoListener listener) {
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).evaluate();
    Node toExecuteIfTrue = arguments.get(1);
    Node toExecuteIfFalse = arguments.get(2);
    if (arg1 != 0.0) {
      return toExecuteIfTrue.evaluate();
    } else {
        return toExecuteIfFalse.evaluate();
    }
  }
}
