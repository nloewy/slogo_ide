package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class IfCommand implements Command {

  public static final int NUM_ARGS = 2;
  private final ModelState modelState;
  private final SlogoListener listener;

  public IfCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    Node toExecute = arguments.get(1);
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
  }

}