package slogo.model.exceptions;

/**
 * The DivideByZeroException class represents an exception that is thrown when attempting to access
 * an index that is not currently a key in myPalette. This occurs during evaluation step of paring.
 * It extends the InvalidOperandException class.
 *
 * @author Noah Loewy
 */

public class IndexNotOnPaletteException extends InvalidOperandException {

  private final String myToken;

  public IndexNotOnPaletteException(String s, String token) {
    super(s);
    myToken = token;
  }

  public String getToken() {
    return myToken;
  }
}
