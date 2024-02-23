package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class ArcTangentCommand extends Command {

  private final Turtle myTurtle;

  public ArcTangentCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return MathUtils.toDegrees(Math.atan(MathUtils.toRadians(arguments.get(0).getValue())));
  }


  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}
