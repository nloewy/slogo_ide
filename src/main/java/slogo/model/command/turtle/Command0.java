package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.command.Command;
import slogo.model.node.Node;

public abstract class Command0 extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return this.execute1(arguments.get(0));
  }

  public abstract Function<ModelState, Double> execute1(Node node0)
      throws InvocationTargetException, IllegalAccessException;

  public int numArgs() {
    return 0;
  }

}
