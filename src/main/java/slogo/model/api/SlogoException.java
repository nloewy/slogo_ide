package slogo.model.api;

/**
 * General wrapper for common exceptions within the Slogo Model.
 *
 * @author Noah Loewy
 */
public class SlogoException extends RuntimeException {

  private final String token;
  private Throwable cause;

  /**
   * Constructs SlogoException.
   * @param message for programmers purposes - not for user.
   * @param token relevant token that caused error, to be later shown to user
   */

  public SlogoException(String message, String token) {
    super(message);
    this.token = token;
  }

  /**
   * Constructs SlogoException.
   * @param message for programmers purposes - not for user.
   * @param cause exception that caused the wrapped exception that caused SlogoException.
   * @param token relevant token that caused error, to be later shown to user
   */

  public SlogoException(String message, Throwable cause, String token) {
    super(message);
    this.cause = cause;
    this.token = token;
  }

  /**
   * Retrieves the exception that caused the SlogoException.
   *
   * @return the exception that caused the SlogoException.
   */

  public Throwable getCause() {
    return cause;
  }

  /**
   * Retrieves the token associated with the exception.
   *
   * @return the token associated with the exception.
   */

  public String getToken() {
    return token;
  }
}

