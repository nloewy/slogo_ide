package slogo.model.command.query;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class HeadingCommand extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return modelState -> {
      return modelState.getTurtles().get(0).getHeading();
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
