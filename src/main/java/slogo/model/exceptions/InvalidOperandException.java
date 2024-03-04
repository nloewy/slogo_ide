package slogo.model.exceptions;

/**
 * The InvalidOperandException is a runtime exception that occurs when a command attempts to perform
 * an operation with operands that result in undefined behavior or are not supported by the
 * operation's logic. This exception is typically thrown when a command leads to an undefined
 * operation, such as dividing by zero or taking the square root of a negative number.
 *
 * @author Noah Loewy
 */

public class InvalidOperandException extends RuntimeException {

  public InvalidOperandException(String s) {
    super(s);
  }
}
