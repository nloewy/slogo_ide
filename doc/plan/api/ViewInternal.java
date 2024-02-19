
public interface ViewInternal {

  public void setColor();
  public void setLanguage();
  public boolean hasCommandString();
  public String getCommandString();
  // Within update, there will be private methods specific to user changes in the view
  public void executeView(CommandData commandData);
  public void displayeErrorMessage( );
  public void displaySettingsPanel();
  public void displaySavePanel();
  public void displayHelpPanel();
  public void displayHistoryPanel();
}