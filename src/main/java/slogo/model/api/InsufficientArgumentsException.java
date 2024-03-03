package slogo.model.api;

public class InsufficientArgumentsException extends SlogoException {

  public InsufficientArgumentsException(String s, String token) {
    super(s, token);
  }
  public String getToken() {
    return super.getToken();
  }
}
