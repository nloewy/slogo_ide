package slogo.model.command.turtle;

import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class PenDownCommand extends Command {

  private final Turtle myTurtle;

  public PenDownCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  @Override
  public double execute(List<Node> arguments) {
    myTurtle.setPen(true);
    return 1.0;
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
