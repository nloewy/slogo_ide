package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class LeftCommand extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double degrees = arguments.get(0).getValue();
    return modelState -> {
      Turtle turtle = modelState.getTurtles().get(0);
      turtle.setHeading(turtle.getHeading() - degrees);
      return degrees;
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
