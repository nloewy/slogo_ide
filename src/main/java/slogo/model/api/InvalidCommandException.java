package slogo.model.api;

public class InvalidCommandException extends RuntimeException {

  public InvalidCommandException(String s) {
    super(s);
  }
}
