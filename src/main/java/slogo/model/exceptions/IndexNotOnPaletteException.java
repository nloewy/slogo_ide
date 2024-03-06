package slogo.model.exceptions;

public class IndexNotOnPaletteException extends InvalidOperandException {


  private String myToken;
  public IndexNotOnPaletteException(String s, String token) {
    super(s);
    myToken = token;
  }
  public String getToken() {
    return myToken;
  }
}
