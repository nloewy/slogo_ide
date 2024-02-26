package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import slogo.model.ModelState;
import slogo.model.SlogoListener;

public class VariableNode extends Node {

  private final String myToken;
  private final ModelState myModelState;

  public VariableNode(String token, ModelState modelState, SlogoListener listener) {
    myModelState = modelState;
    myToken = token.toLowerCase();
  }

  @Override
  public double getValue() throws InvocationTargetException, IllegalAccessException {
    return myModelState.getVariables().getOrDefault(myToken, 0.0);
  }

  @Override
  public String getToken() {
    return myToken;
  }
}


