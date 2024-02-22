package slogo.model;

import java.lang.reflect.InvocationTargetException;

public class ConstantNode extends Node {

  private double myConstant;
  public ConstantNode(String token, Turtle turtle) {
    myConstant = Double.parseDouble(token);
  }

  public double getValue() throws InvocationTargetException, IllegalAccessException {
    return myConstant;
  }

}
