package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;

public class ListNode extends Node {


  private final String myToken;

  public ListNode(String token, ModelState model, SlogoListener listener) {
    myToken = token;
  }

  @Override
  public double getValue() throws InvocationTargetException, IllegalAccessException {
    double ret = 0.0;
    for (Node child : getChildren()) {
      ret = child.getValue();
    }
    return ret;
  }

  public String getToken() {
    return myToken;
  }


}
