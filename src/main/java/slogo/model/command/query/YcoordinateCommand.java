package slogo.model.command.query;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.command.Command;

public class YcoordinateCommand extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return modelState -> {
      return modelState.getTurtles().get(0).getY();
    };
  }

  @Override
  public int getNumArgs() {
    return 0;
  }

  /**@Override public void notifyListener(SlogoListener listener, double value) {
  super.notifyListener(listener, value);
  listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }
   */
}
