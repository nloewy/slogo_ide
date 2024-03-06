package slogo.model.command.exceptions;

import slogo.model.exceptions.InvalidOperandException;

public class InvalidRandomInputException extends InvalidOperandException {

  public InvalidRandomInputException(String s) {
    super(s);
  }
}
