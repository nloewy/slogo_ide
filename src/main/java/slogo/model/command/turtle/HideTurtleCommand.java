package slogo.model.command.turtle;

import java.util.List;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class HideTurtleCommand extends Command {

  private Turtle myTurtle;

  public HideTurtleCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  @Override
  public double execute(List<Double> arguments) {
    myTurtle.setVisible(false);
    return 0.0;
  }

}
