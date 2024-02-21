public interface ViewInternal {

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
  public void onUpdateTurtleState(ImmutableTurtle turtle);

  /*
   * Overrides a method from higher in the inheritance
   * tree. Handles the user changing the value of a
   * previously defined variable.
   */
  public void onUpdateValue(String text, double value);

  /*
   * Handles the user switching languages.
   */
  public void setLanguage(String lang);

  /*
   * Handles the user switching background color.
   */
  public void setColor(Color color);

  /*
   * Checks if the text field has at least
   * one valid command in it.
   */
  public boolean hasCommandString();

  /*
   * Returns the current text field body.
   */
  public String getCommandString();

  /*
   * Gets the current exception, and shows
   * it as an alert.
   */
  public void displayErrorMessage();

  /*
   * Gets the current settings, and shows
   * it as a panel.
   */
  public void displaySettingsPanel();

  /*
   * Gets the current XMl file, and shows
   * it as an panel.
   */
  public void displaySavePanel();

  /*
   * Gets the current documentation, and shows
   * it as an panel.
   */
  public void displayHelpPanel();

  /*
   * Gets the current command history, and shows
   * it as an panel.
   */
  public void displayHistoryPanel();

  /**
   * Resets all panels in the view
   */
  public void resetView();
}