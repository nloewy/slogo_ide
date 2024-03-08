package slogo.model.command.control;

import slogo.model.ModelState;
import slogo.model.node.Node;

public class LoopCommandHandler {
  private double start;
  private double end;
  private double increment;

  public void setLoopParameters(double start, double end, double increment) {
    this.start = start;
    this.end = end;
    this.increment = increment;
  }

  public void setLoopParameters(double end) {
    this.setLoopParameters(1, end, 1);
  }

  public double runLoop(Node commands, ModelState modelState, String variableName) {
    double res = 0.0;
    for (double idx = start; idx <= end; idx += increment) {
      modelState.getVariables().put(variableName, idx);
      res = commands.evaluate();
      modelState.getVariables().remove(variableName);
    }
    return res;
  }
}
