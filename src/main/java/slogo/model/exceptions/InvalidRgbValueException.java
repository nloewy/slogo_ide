package slogo.model.exceptions;

/**
 * The InvalidRgbValueException class represents an exception that is thrown when attempting to add
 * an invalid color to palette. An invalid color means that one of the arguments for RGB values is
 * not between 0 and 255. This occurs during evaluation step of paring. It extends the
 * InvalidOperandException class.
 *
 * @author Noah Loewy
 */

public class InvalidRgbValueException extends InvalidOperandException {

  private final String myToken;

  public InvalidRgbValueException(String s, String token) {
    super(s);
    myToken = token;
  }

  public String getToken() {
    return myToken;
  }


}
