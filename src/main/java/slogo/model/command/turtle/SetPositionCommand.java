package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class SetPositionCommand extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double newX = arguments.get(0).getValue();
    double newY = arguments.get(1).getValue();

    return modelState -> {
      Turtle turtle = modelState.getTurtles().get(0);
      double currentX = turtle.getX();
      double currentY = turtle.getY();
      turtle.setX(newX);
      turtle.setY(newY);
      return MathUtils.dist(turtle.getX(), turtle.getY(), currentX, currentY);
    };
  }

  @Override
  public int getNumArgs() {
    return 2;
  }

  /**@Override public void notifyListener(SlogoListener listener, double value) {
  super.notifyListener(listener, value);
  listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }
   */

}
