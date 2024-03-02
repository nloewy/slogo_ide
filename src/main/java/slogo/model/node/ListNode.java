package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import slogo.model.ModelState;

public class ListNode extends Node {


  private final String myToken;

  public ListNode(String token, ModelState model) {
    myToken = token;
  }

  @Override
  public double evaluate() throws InvocationTargetException, IllegalAccessException {
    double ret = 0.0;
    for (Node child : getChildren()) {
      ret = child.evaluate();
    }
    return ret;
  }

  public String getToken() {
    return myToken;
  }


}
