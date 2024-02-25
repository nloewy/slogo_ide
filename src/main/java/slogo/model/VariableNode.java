package slogo.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.command.Command;
import slogo.model.command.control.MakeCommand;

public class VariableNode extends Node {

  private final String myToken;
  private double myValue;
  private final ModelState myModelState;

  public VariableNode(String token, ModelState modelState) {
    myModelState = modelState;
    myToken = token;
  }

  @Override
  public double getValue() throws InvocationTargetException, IllegalAccessException {
    myModelState.getVariables().putIfAbsent(myToken, 0.0);
    List<Node> children = getChildren();
    Command variableMaker = new MakeCommand();
    Function<ModelState, Double> function = variableMaker.execute(children);
    double ans = myModelState.applyCommandToModelState(function);
    myModelState.getVariables().put(myToken, ans);
    return ans;
  }

  @Override
  public String getToken() {
    return null;
  }
}
