package slogo.model;

import java.lang.reflect.InvocationTargetException;

public class ConstantNode extends Node {

  private final double myConstant;

  public ConstantNode(String token, Turtle turtle) throws NumberFormatException {
    myConstant = Double.parseDouble(token);
  }

  public double getValue() throws InvocationTargetException, IllegalAccessException {
    return myConstant;
  }

}
