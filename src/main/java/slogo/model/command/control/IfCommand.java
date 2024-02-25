package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.command.Command;

public class IfCommand extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    Node toExecute = arguments.get(1);
    return modelState -> {
      if (arg1 != 0) {
        try {
          return toExecute.getValue();
        } catch (InvocationTargetException e) {
          throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
      return 0.0;
    };
  }
}
/**
 * public void notifyListener(SlogoListener listener, double value) { super.notifyListener(listener,
 * value); }
 * <p>
 * }
 */