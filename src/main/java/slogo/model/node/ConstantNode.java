package slogo.model.node;

import slogo.model.ModelState;

/**
 * Represents a constant value node in the syntax tree. This node stores a constant numerical value
 * and returns it upon evaluation.
 *
 * @author Noah Loewy
 */

public class ConstantNode extends Node {

  private final double myConstant;
  private final String myToken;

  /**
   * Constructs a ConstantNode with the specified token and model state. Parses the token to
   * retrieve the constant numerical value and stores it internally.
   *
   * @param token      the token representing the constant value
   * @param modelState the model state associated with this node
   */

  public ConstantNode(String token, ModelState modelState) {
    super();
    myToken = token;
    myConstant = Double.parseDouble(token);
  }

  /**
   * Returns value of constant node
   *
   * @return myConstant
   */
  @Override
  public double evaluate() {

    return myConstant;
  }

  /**
   * Returns token of node
   *
   * @return the token representing constant node (its value)
   */
  @Override
  public String getToken() {
    return myToken;
  }
}
