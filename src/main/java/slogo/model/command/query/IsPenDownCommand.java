package slogo.model.command.query;

import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class IsPenDownCommand extends Command {

  private final Turtle myTurtle;

  public IsPenDownCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Node> arguments) {
    if (myTurtle.getPen()) {
      return 1.0;
    }
    return 0.0;

  }

  public int getNumberOfArgs() {
    return 0;
  }
  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }

}
