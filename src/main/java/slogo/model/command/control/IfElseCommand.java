package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.node.Node;
import slogo.model.command.Command;

public class IfElseCommand extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    Node toExecuteIfTrue = arguments.get(1);
    Node toExecuteIfFalse = arguments.get(2);
    return modelState -> {
      if (arg1 != 0.0) {
        try {
          return toExecuteIfTrue.getValue();
        } catch (InvocationTargetException | IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      } else {
        try {
          return toExecuteIfFalse.getValue();
        } catch (InvocationTargetException | IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    };
  }

  @Override
  public int getNumArgs() {
    return 3;
  }

}
/**
 * public void notifyListener(SlogoListener listener, double value) { super.notifyListener(listener,
 * value); }
 * <p>
 * }
 */