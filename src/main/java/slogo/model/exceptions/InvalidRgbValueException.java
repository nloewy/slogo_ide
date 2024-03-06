package slogo.model.exceptions;

public class InvalidRgbValueException extends InvalidOperandException {
  private String myToken;
  public InvalidRgbValueException(String s, String token) {
    super(s);
    myToken = token;
  }

  public String getToken() {
    return myToken;
  }


}
