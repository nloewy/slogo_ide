package slogo.model.api;

/**
 * General wrapper for common exceptions within the Slogo Model.
 */
public class SlogoException extends RuntimeException {

  private final String token;

  public SlogoException(String message, String token) {
    super(message);
    this.token = token;
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
