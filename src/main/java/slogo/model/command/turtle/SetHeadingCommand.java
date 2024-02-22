package slogo.model.command.turtle;

import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class SetHeadingCommand extends Command {

  private final Turtle myTurtle;

  public SetHeadingCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  @Override
  public double execute(List<Node> arguments) {
    double heading = arguments.get(0).getValue();
    double oldHeading = myTurtle.getHeading();
    myTurtle.setHeading(heading % 360);
    return (myTurtle.getHeading() - oldHeading + 360) % 360;
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
