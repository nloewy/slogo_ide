package slogo.model.api;

public class InvalidCommandException extends SlogoException {
  public InvalidCommandException(String s, String token) {
    super(s, token);
  }
  public String getToken() {
    return super.getToken();
  }
}
