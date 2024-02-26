package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import slogo.model.ModelState;

public class ConstantNode extends Node {

  private final double myConstant;
  private final String myToken;

  public ConstantNode(String token, ModelState modelState) throws NumberFormatException {
    super();
    myToken = token;
    myConstant = Double.parseDouble(token);
  }

  public double getValue() throws InvocationTargetException, IllegalAccessException {
    return myConstant;
  }
}
