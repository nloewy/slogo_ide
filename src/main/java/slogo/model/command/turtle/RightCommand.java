package slogo.model.command.turtle;

import java.util.List;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class RightCommand extends Command {

  private Turtle myTurtle;

  public RightCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  @Override
  public double execute(List<Double> arguments) {
    double degrees = arguments.get(0);
    myTurtle.setHeading((myTurtle.getHeading() + degrees) % 360);
    return degrees;
  }
}