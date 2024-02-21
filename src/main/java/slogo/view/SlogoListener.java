package slogo.view;

public interface SlogoListener {
     /*
   * Overrides a method from higher in the inheritance
   * tree. Handles the user creating a custom variable.
   */
  public void onUpdateVariable(String variableName, double value);

  /*
   * Overrides a method from higher in the inheritance
   * tree. Handles the user changing the turtle's position.
   * 
   * Passed a turtle object from the backend/controller and changes
   * display as needed. For test, pass a random turtle object.
   * 
   * FrontEndTurtle: keeps track of all data that is needed to display the turtle. This includes, but
is not limited to, the actual ImageView object the turtle is represented by, the coordinates of the
turtle, the orientation of the turtle, and the pen color of the turtle.

implement the view classes today spend the time you got
   */
  public void onUpdateTurtleState(FrontEndTurtle turtle);

  /*
   * Overrides a method from higher in the inheritance
   * tree. Handles the user changing the value of a
   * previously defined variable.
   */
  public void onUpdateValue(String text, double value);
}
