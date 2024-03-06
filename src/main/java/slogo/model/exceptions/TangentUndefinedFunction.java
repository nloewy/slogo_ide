package slogo.model.exceptions;

import slogo.model.exceptions.InvalidOperandException;

public class TangentUndefinedFunction extends InvalidOperandException {

  public TangentUndefinedFunction(String s) {
    super(s);
  }
}
