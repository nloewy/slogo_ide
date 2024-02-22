package slogo.model.command.turtle;

import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class BackwardCommand extends Command {

  private final Turtle myTurtle;

  public BackwardCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Double> arguments) {
    double pixels = arguments.get(0);
    myTurtle.setX(myTurtle.getX() - pixels * Math.sin(MathUtils.toRadians(myTurtle.getHeading())));
    myTurtle.setY(myTurtle.getY() - pixels * Math.cos(MathUtils.toRadians(myTurtle.getHeading())));
    return pixels;
  }

  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }

}
