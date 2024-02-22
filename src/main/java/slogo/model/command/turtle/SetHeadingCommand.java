package slogo.model.command.turtle;

import java.util.List;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class SetHeadingCommand extends Command {

  private Turtle myTurtle;

  public SetHeadingCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  @Override
  public double execute(List<Double> arguments) {
    double heading = arguments.get(0);
    double oldHeading = myTurtle.getHeading();
    myTurtle.setHeading(heading % 360);
    return (myTurtle.getHeading() - oldHeading + 360) % 360 ;
  }

}
