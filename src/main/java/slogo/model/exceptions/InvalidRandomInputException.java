package slogo.model.exceptions;

import slogo.model.exceptions.InvalidOperandException;

public class InvalidRandomInputException extends InvalidOperandException {

  public InvalidRandomInputException(String s) {
    super(s);
  }
}
