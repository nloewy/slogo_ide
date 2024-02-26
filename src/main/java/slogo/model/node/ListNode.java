package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import slogo.model.ModelState;
import slogo.model.SlogoListener;

public class ListNode extends Node {


  public ListNode(String token, ModelState model, SlogoListener listener) {
  }

  @Override
  public double getValue() throws InvocationTargetException, IllegalAccessException {
    double ret = 0.0;
    for (Node child : getChildren()) {
      ret = child.getValue();
    }
    return ret;
  }


}
