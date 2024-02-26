package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import slogo.model.ModelState;

public class VariableNode extends Node {

  private final String myToken;
  private final ModelState myModelState;

  public VariableNode(String token, ModelState modelState) {
    myModelState = modelState;
    myToken = token;
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


