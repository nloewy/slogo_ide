package slogo.model.exceptions;

import slogo.model.api.SlogoException;

public class InvalidTokenException extends SlogoException {

  public InvalidTokenException(String s, String token) {
    super(s, token);
  }

  public String getToken() {
    return super.getToken();
  }
}
