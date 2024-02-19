public interface ViewInternal {

  public void onUpdateVariable(String variableName, double value);
  public void onUpdateTurtleState(ImmutableTurtle turtle);
  public void onUpdateValue(String text, double value);
  public boolean hasCommandString();
  public String getCommandString();
  public void displayeErrorMessage( );
  public void displaySettingsPanel();
  public void displaySavePanel();
  public void displayHelpPanel();
  public void displayHistoryPanel();
}