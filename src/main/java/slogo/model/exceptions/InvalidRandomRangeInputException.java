package slogo.model.exceptions;

/**
 * The InvalidRandomRangeInput class represents an exception that is thrown when attempting to
 * select a random number between inputs MIN and MAX. If the input MAX exceeds MIN, this exception
 * is thrown. This occurs during evaluation step of paring. It extends the InvalidOperandException
 * class.
 *
 * @author Noah Loewy
 */

public class InvalidRandomRangeInputException extends InvalidOperandException {

  public InvalidRandomRangeInputException(String s) {
    super(s);
  }
}
