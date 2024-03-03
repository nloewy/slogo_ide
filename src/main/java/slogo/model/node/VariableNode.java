package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;

public class VariableNode extends Node {

  private final String myToken;
  private final ModelState myModelState;

  public VariableNode(String token, ModelState modelState) {
    myModelState = modelState;
    myToken = token.toLowerCase();
  }

  @Override
  public double evaluate() throws InvocationTargetException, IllegalAccessException {
    if (!myModelState.getVariables().containsKey(myToken)) {
      getListener().onUpdateValue(myToken, 0.0);
      myModelState.getVariables().put(myToken, 0.0);
    }
    return myModelState.getVariables().getOrDefault(myToken, 0.0);

  }

  @Override
  public String getToken() {
    return myToken;
  }
}


