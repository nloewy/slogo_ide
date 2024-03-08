package slogo.model.command.control;

import slogo.model.ModelState;
import slogo.model.node.Node;

public class LoopCommand {

  public double runLoop(double start, double end, double increment, Node commands, ModelState model,
      String variable) {
    double res = 0.0;
    for (double idx = start; idx <= end; idx += increment) {
      model.getVariables().put(variable, idx);
      res = commands.evaluate();
      model.getVariables().remove(variable);
    }
    return res;
  }

}
