package slogo.model.command.math;

import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class CosineCommand extends Command {

  private final Turtle myTurtle;

  public CosineCommand(Turtle turtle) {
    myTurtle = turtle;
  }
  public double execute(List<Node> arguments) {
    return Math.cos(MathUtils.toRadians(arguments.get(0)));

  }

  public int getNumberOfArgs() {
    return 1;
  }
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}
