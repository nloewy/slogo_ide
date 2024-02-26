package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class ForCommand extends Command {

  private final ModelState modelState;
  private final SlogoListener listener;

  public ForCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    String variableName = arguments.get(0).getChildren().get(0).getToken();
    double start = arguments.get(0).getChildren().get(1).getValue();
    double end = arguments.get(0).getChildren().get(2).getValue();
    double increment = arguments.get(0).getChildren().get(3).getValue();
    Node commands = arguments.get(1);
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
