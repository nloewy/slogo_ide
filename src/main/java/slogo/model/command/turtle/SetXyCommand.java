package slogo.model.command.turtle;

import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class SetXyCommand extends Command {

  private final Turtle myTurtle;

  public SetXyCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  @Override
  public double execute(List<Double> arguments) {
    double currentX = myTurtle.getX();
    double currentY = myTurtle.getY();
    myTurtle.setX(arguments.get(0));
    myTurtle.setY(arguments.get(1));
    return MathUtils.dist(myTurtle.getX(), myTurtle.getY(), currentX, currentY);
  }

  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }


}
