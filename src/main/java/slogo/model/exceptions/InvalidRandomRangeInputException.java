package slogo.model.exceptions;

import slogo.model.exceptions.InvalidOperandException;

public class InvalidRandomRangeInputException extends InvalidOperandException {

  public InvalidRandomRangeInputException(String s) {
    super(s);
  }
}
