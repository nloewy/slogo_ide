package slogo.model.api;

public class SlogoException extends RuntimeException {

  private final String myToken;

  public SlogoException(String s, String token) {
    super(s);
    myToken = token;
  }

  public String getToken() {
    return myToken;
  }

}
