package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import slogo.model.ModelState;
import slogo.model.SlogoListener;

public class VariableNode extends Node {

  private final String myToken;
  private final ModelState myModelState;
  private final SlogoListener myListener;


  public VariableNode(String token, ModelState modelState, SlogoListener listener) {
    myModelState = modelState;
    myToken = token.toLowerCase();
    myListener = listener;
  }

  @Override
  public double getValue() throws InvocationTargetException, IllegalAccessException {
    if (!myModelState.getVariables().containsKey(myToken)) {
      myListener.onUpdateValue(myToken, 0.0);
      myModelState.getVariables().put(myToken, 0.0);
    }
    return myModelState.getVariables().getOrDefault(myToken, 0.0);

  }

  @Override
  public String getToken() {
    return myToken;
  }
}


