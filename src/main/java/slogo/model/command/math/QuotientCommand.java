package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class QuotientCommand extends Command {

  private final Turtle myTurtle;

  public QuotientCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    if (arguments.get(1).getValue() == 0) {
      return 0;
      //  throw new ArithmeticException("Divisor must be non-zero");
      //  return 0.0
      //}
    }
    return arguments.get(0).getValue() / arguments.get(1).getValue();
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}
