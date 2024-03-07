package slogo.model.exceptions;

import slogo.model.api.SlogoException;

/**
 * The InvalidUserCommandException class represents an exception that is thrown when attempting to
 * create a UserCommand that is either incorrectly formatted, or already exists as a command in
 * Base Slogo. This occurs during the parsing step. It extends the SlogoException class.
 *
 * @author Noah Loewy
 */

public class InvalidUserCommandException extends SlogoException {

  public InvalidUserCommandException(String message, String token) {
    super(message, token);
  }
}
