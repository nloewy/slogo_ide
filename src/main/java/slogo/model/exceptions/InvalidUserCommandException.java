package slogo.model.exceptions;

import slogo.model.api.SlogoException;

public class InvalidUserCommandException extends SlogoException {

  public InvalidUserCommandException(String message, String token) {
    super(message, token);
  }
}
