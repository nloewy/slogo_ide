package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class ForwardCommand extends Command {

  private final Turtle myTurtle;

  public ForwardCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  @Override
  public double execute(List<Node> arguments)
      throws IllegalAccessException, InvocationTargetException {
    double pixels = arguments.get(0).getValue();
    myTurtle.setX(myTurtle.getX() + pixels * Math.sin(MathUtils.toRadians(myTurtle.getHeading())));
    myTurtle.setY(myTurtle.getY() + pixels * Math.cos(MathUtils.toRadians(myTurtle.getHeading())));
    return pixels;
  }

  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }
}
