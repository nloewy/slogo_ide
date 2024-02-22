package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class MinusCommand extends Command {

  private final Turtle myTurtle;

  public MinusCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return -1 * arguments.get(0).getValue();
  }

  public int getNumberOfArgs() {
    return 2;
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}
