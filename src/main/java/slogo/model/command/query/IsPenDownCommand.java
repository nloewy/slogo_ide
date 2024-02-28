package slogo.model.command.query;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class IsPenDownCommand implements Command {

  public static final int NUM_ARGS = 0;

  private final ModelState modelState;

  public IsPenDownCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    Turtle turtle = modelState.getTurtles().get(0);
    if (turtle.getPen()) {
      return 1.0;
    }
    return 0.0;
  }
}
