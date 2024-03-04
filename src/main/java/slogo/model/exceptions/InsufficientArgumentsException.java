package slogo.model.exceptions;

import slogo.model.api.SlogoException;

/**
 * The InsufficientArgumentException is a runtime exception that occurs when a command expects a
 * specific number of arguments, but fewer than that number are provided to the Slogo parser. This
 * exception indicates that the command did not receive the required number of arguments to perform
 * its operation correctly.
 *
 * @author Noah Loewy
 */

public class InsufficientArgumentsException extends SlogoException {

  public InsufficientArgumentsException(String s, String token) {
    super(s, token);
  }

  public String getToken() {
    return super.getToken();
  }
}
