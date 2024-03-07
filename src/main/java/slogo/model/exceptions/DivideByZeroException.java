package slogo.model.exceptions;

/**
 * The DivideByZeroException class represents an exception that is thrown when attempting to divide
 * by zero. This occurs during evaluation step of paring. It extends the InvalidOperandException
 * class.
 *
 * @author Noah Loewy
 */

public class DivideByZeroException extends InvalidOperandException {

  public DivideByZeroException(String s) {
    super(s);
  }
}
