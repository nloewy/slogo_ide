package slogo.model.command.bool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class EqualCommand extends Command {

  private final Turtle myTurtle;

  public EqualCommand(Turtle turtle) {
    myTurtle = turtle;
  }
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return (arguments.get(0).getValue() == arguments.get(1).getValue()) ? 1 : 0;

  }
  public int getNumberOfArgs() {
    return 2;
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}
