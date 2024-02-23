package slogo.model.command.query;

import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class XcoordinateCommand extends Command {

  private final Turtle myTurtle;

  public XcoordinateCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Node> arguments) {
    return myTurtle.getX();
  }

  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }
}
