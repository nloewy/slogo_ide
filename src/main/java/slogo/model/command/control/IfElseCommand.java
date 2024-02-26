package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class IfElseCommand extends Command {
  public static final int NUM_ARGS = 3;
  private final ModelState modelState;
  private final SlogoListener listener;

  public IfElseCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    Node toExecuteIfTrue = arguments.get(1);
    Node toExecuteIfFalse = arguments.get(2);
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
  }

  
}
/**
 * public void notifyListener(SlogoListener listener, double value) { super.notifyListener(listener,
 * value); }
 * <p>
 * }
 */