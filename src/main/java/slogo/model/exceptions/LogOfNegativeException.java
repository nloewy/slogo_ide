package slogo.model.exceptions;

/**
 * The LogOfNegativeException class represents an exception that is thrown when attempting to take
 * the natural logarithm of a negative number. This occurs during evaluation step of paring. It
 * extends the InvalidOperandException class.
 *
 * @author Noah Loewy
 */

public class LogOfNegativeException extends InvalidOperandException {

  public LogOfNegativeException(String s) {
    super(s);
  }

}
