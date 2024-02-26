package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class MakeCommand extends Command {
  public static final int NUM_ARGS = 2;
  private final ModelState modelState;
  private final SlogoListener listener;

  public MakeCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    String token = arguments.get(0).getToken();
    double arg1 = arguments.get(1).getValue();
    modelState.getVariables().put(token, arg1);
    listener.onUpdateValue(token,arg1);
    return arg1;
  }

  
}
/**
 * public void notifyListener(SlogoListener listener, double value) { super.notifyListener(listener,
 * value); }
 * <p>
 * }
 */