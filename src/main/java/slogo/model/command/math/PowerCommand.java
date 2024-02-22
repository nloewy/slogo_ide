package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class PowerCommand extends Command {
  private final Turtle myTurtle;

  public PowerCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double base = arguments.get(0).getValue();
    double exp = arguments.get(1).getValue();
    return Math.pow(base, exp);

  }

  public int getNumberOfArgs() {
    return 2;
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}
