package slogo.model.command.turtle;

import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class LeftCommand extends Command {

  private final Turtle myTurtle;

  public LeftCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  @Override
  public double execute(List<Node> arguments) {
    double degrees = arguments.get(0);
    myTurtle.setHeading((myTurtle.getHeading() - degrees) % 360);
    return degrees;
  }

  public int getNumberOfArgs() {
    return 1;
  }
  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }

}
