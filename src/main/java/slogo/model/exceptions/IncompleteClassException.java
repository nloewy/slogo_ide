package slogo.model.exceptions;

/**
 * The IncompleteClassException is a runtime exception that occurs when a concrete command class
 * does not define the necessary public static final variable NUM_ARGS. This exception indicates
 * that the command class is incomplete or improperly implemented, as the NUM_ARGS variable is
 * needed to determine the number of arguments expected by the command via reflection.
 *
 * @author Noah Loewy
 */
public class IncompleteClassException extends RuntimeException {

  public IncompleteClassException(String s) {
    super(s);
  }
}
