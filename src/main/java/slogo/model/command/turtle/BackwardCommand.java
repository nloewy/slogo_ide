package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class BackwardCommand extends Command {

  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double pixels = arguments.get(0).getValue();
    return modelState -> {
      Turtle turtle = modelState.getTurtles().get(0);
      double newX = turtle.getX() - pixels * Math.sin(Math.toRadians(turtle.getHeading()));
      double newY = turtle.getY() - pixels * Math.cos(Math.toRadians(turtle.getHeading()));
      turtle.setX(newX);
      turtle.setY(newY);
      return pixels;
    };
  }

  /**
  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }
*/

}
