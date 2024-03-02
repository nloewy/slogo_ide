package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class RepeatCommand implements Command {

  public static final int NUM_ARGS = 2;
  private final ModelState modelState;

  public RepeatCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    String variableName = ":repcount";
    double end = arguments.get(0).evaluate();
    Node commands = arguments.get(1);
    double res = 0.0;
    for (double i = 1; i <= end; i += 1) {
      modelState.getVariables().put(variableName, i);
      res = commands.evaluate();
    }
    modelState.getVariables().remove(":repcount");
    return res;
  }
}
