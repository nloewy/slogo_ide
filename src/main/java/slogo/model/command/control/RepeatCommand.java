package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.api.Node;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class RepeatCommand extends Command {


  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    String variableName = "repcount";
    double end = arguments.get(0).getValue();
    Node commands = arguments.get(1);
    return modelState -> {
      double holder = modelState.getVariables().getOrDefault("repcount",Double.MAX_VALUE);
      double res = 0.0;
      for (double i = 1; i <= end; i += 1) {
        modelState.getVariables().put(variableName, i);
        try {
          res = commands.getValue();
        } catch (InvocationTargetException | IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
      if(holder == Double.MAX_VALUE) { holder = end; }
      modelState.getVariables().put("repcount", holder);
      return res;
    };

  }

  @Override
  public int getNumArgs() {
    return 2;
  }

  public void notifyListener(SlogoListener listener, double value) {

    //super.notifyListener(listener, value);
  }
}
