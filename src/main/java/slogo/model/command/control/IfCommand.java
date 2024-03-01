package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class IfCommand implements Command {

  public static final int NUM_ARGS = 2;

  public IfCommand(ModelState modelState, SlogoListener listener) {
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    Node toExecute = arguments.get(1);
    if (arg1 != 0) {
      return toExecute.getValue();
    }
    return 0.0;
  }

}