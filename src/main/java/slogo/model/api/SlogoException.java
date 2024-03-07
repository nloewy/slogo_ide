package slogo.model.api;

/**
 * General wrapper for common exceptions within the Slogo Model.
 */
public class SlogoException extends RuntimeException {

  private final String token;
  private Throwable cause;

  public SlogoException(String message, String token) {
    super(message);
    this.token = token;
  }

  public SlogoException(String message, Throwable cause, String token) {
    super(message);
    this.cause = cause;
    this.token = token;
  }

  public Throwable getCause() {
    return cause;
  }

  /**
   * Retrieves the token associated with the exception.
   *
   * @return the token associated with the exception
   */
  public String getToken() {
    return token;
  }
}

