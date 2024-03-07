package slogo.model.exceptions;

/**
 * The UndefinedExponentException class represents an exception that is thrown when attempting to
 * do an exponential operation that would cause an undefined value. This occurs during evaluation
 * step of paring. It extends the InvalidOperandException class.
 *
 * @author Noah Loewy
 */
public class UndefinedExponentException extends InvalidOperandException {

  public UndefinedExponentException(String s) {
    super(s);
  }
}
