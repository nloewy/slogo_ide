package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class SetHeadingCommand extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double newHeading = arguments.get(0).getValue();

    return modelState -> {
      Turtle turtle = modelState.getTurtles().get(0);
      double oldHeading = turtle.getHeading();
      turtle.setHeading(newHeading);
      double clockwiseTurn = (newHeading - oldHeading + 360) % 360;
      double counterclockwiseTurn = (oldHeading - newHeading + 360) % 360;
      return Math.min(Math.abs(clockwiseTurn), Math.abs(counterclockwiseTurn));
    };
  }

  @Override
  public int getNumArgs() {
    return 1;
  }

  /**@Override public void notifyListener(SlogoListener listener, double value) {
  super.notifyListener(listener, value);
  listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }
   */

}
