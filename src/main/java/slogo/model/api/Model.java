package slogo.model.api;

import java.lang.reflect.InvocationTargetException;
import slogo.model.exceptions.InvalidTokenException;

/**
 * @author Noah Loewy
 */
public interface Model {

  /**
   * Parses a command string to create a syntax tree, and then executes the tree,
   *
   * @param commandStr input string of tokens to be parsed
   */
  void parse(String commandStr)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException, InvalidTokenException;

  /**
   * Resets the model to its initial state.
   */
  void resetModel();
}
