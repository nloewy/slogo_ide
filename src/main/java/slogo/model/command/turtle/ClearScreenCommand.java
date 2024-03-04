package slogo.model.command.turtle;

import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.api.TurtleRecord;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class ClearScreenCommand extends HomeCommand {

  public static final int NUM_ARGS = 0;
  private final ModelState modelState;
  private final SlogoListener listener;

  public ClearScreenCommand(ModelState modelState, SlogoListener listener) {
    super(modelState, listener);
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments) {
    double ret = super.execute(arguments);
    listener.onResetTurtle(modelState.getTurtles().get(0).getImmutableTurtle().id());
    return ret;
  }
}



