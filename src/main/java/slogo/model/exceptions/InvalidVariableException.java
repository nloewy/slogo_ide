package slogo.model.exceptions;

import slogo.model.api.SlogoException;

/**
 * The InvalidCommandException is a runtime exception that occurs when a variable is expected and
 * the parser encounters a token that is not recognized as a valid variable in Slogo.
 *
 * @author Noah Loewy
 */

public class InvalidVariableException extends SlogoException {

  public InvalidVariableException(String s, String token) {
    super(s, token);
  }

}
