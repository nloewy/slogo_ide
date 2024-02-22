package slogo.model.command;

import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.Turtle;

public class ForwardCommand extends Command {



  private Turtle myTurtle;

  public ForwardCommand(Turtle turtle) {
    myTurtle = turtle;
  }
  @Override
  public double execute(List<Double> arguments) {
    double pixels = arguments.get(0);
    myTurtle.setX(myTurtle.getX()+ pixels * Math.sin(MathUtils.toRadians(myTurtle.getHeading())));
    myTurtle.setY(myTurtle.getY()+ pixels * Math.cos(MathUtils.toRadians(myTurtle.getHeading())));
    return pixels;
  }
}
