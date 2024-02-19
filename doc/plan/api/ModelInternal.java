
public interface ModelInternal {

  public CommandData getDataForCommand();

  public Command createCommand();

  public void addCommandToHistory(Command command);

}