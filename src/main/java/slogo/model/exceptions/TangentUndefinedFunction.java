package slogo.model.exceptions;

/**
 * The TangentUndefinedFunction class represents an exception that is thrown when attempting to
 * take the tangent root of a number that is an "odd" multiple of 90 degrees. This occurs during
 * evaluation step of paring. It extends the InvalidOperandException class.
 *
 * @author Noah Loewy
 */

public class TangentUndefinedFunction extends InvalidOperandException {

  public TangentUndefinedFunction(String s) {
    super(s);
  }
}
