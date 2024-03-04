package slogo.model.exceptions;

import slogo.model.api.SlogoException;

/**
 * The InvalidTokenException is a runtime exception that occurs when a token entered by the user
 * does not match the regular expression pattern of any recognized token type as defined in the
 * syntax configuration file (Syntax.properties). This exception typically arises during parsing /
 * tokenization
 *
 * @author Noah Loewy
 */

public class InvalidTokenException extends SlogoException {

  public InvalidTokenException(String s, String token) {
    super(s, token);
  }

  public String getToken() {
    return super.getToken();
  }
}
