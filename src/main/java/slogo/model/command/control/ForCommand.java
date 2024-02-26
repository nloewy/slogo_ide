package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.api.Node;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class ForCommand extends Command {


  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    String variableName = arguments.get(0).getChildren().get(0).getToken();
    double start = arguments.get(0).getChildren().get(1).getValue();
    double end = arguments.get(0).getChildren().get(2).getValue();
    double increment = arguments.get(0).getChildren().get(3).getValue();
    Node commands = arguments.get(1);
    return modelState -> {
      double res = 0.0;
      for (double i = start; i <= end; i += increment) {
        modelState.getVariables().put(variableName, i);
        try {
          res = commands.getValue();
        } catch (InvocationTargetException | IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
      return res;
    };
  }


  @Override
  public int getNumArgs() {
    return 2;
  }


  //IN NOTIFY LISTENER MAKE SURE TO UPDATE THE FOR VARIABLE
  public void notifyListener(SlogoListener listener, double value) {

    //super.notifyListener(listener, value);
  }
}
