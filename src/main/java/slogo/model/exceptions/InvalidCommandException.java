package slogo.model.exceptions;

import slogo.model.api.SlogoException;

/**
 * The InvalidCommandException is a runtime exception that occurs when the parser encounters a token
 * that is not recognized as a valid command in the Slogo environment. This exception may occur if
 * the token entered to the parser does not conform to the syntax or format of a valid command, or
 * if the properly formatted command is not recognized as a valid Slogo or user-defined command.
 */

public class InvalidCommandException extends SlogoException {

  public InvalidCommandException(String s, String token) {
    super(s, token);
  }

  public String getToken() {
    return super.getToken();
  }
}
