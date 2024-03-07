package slogo.model.exceptions;

/**
 * The SquareRootOfNegativeException class represents an exception that is thrown when attempting to
 * take the square root of a negative number. This occurs during evaluation step of paring. It
 * extends the InvalidOperandException class.
 *
 * @author Noah Loewy
 */

public class SquareRootOfNegativeException extends InvalidOperandException {

  public SquareRootOfNegativeException(String s) {
    super(s);
  }
}