package slogo.model.exceptions;

/**
 * The InvalidRandomInput class represents an exception that is thrown when attempting to select
 * a random number between 0 and input N. If the input N is less than 0, this exception is thrown.
 * This occurs during evaluation step of paring. It extends the InvalidOperandException class.
 *
 * @author Noah Loewy
 */

public class InvalidRandomInputException extends InvalidOperandException {

  public InvalidRandomInputException(String s) {
    super(s);
  }
}
