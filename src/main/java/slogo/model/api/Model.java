package slogo.model.api;

/**
 * Represents the template for a Slogo Model's external API. Aside from the listener, the view can
 * only interact with the model by parsing and resetting.
 *
 * @author Noah Loewy
 */
public interface Model {

  /**
   * Parses a command string to create a syntax tree, and then executes the tree.
   *
   * @param commandStr input string of tokens to be parsed
   */
  void parse(String commandStr) throws SlogoException;

  /**
   * Resets the model to its initial state.
   */
  void resetModel();
}
