public interface ModelInternal {

  Command createCommand();

  void addCommandToHistory(Command command);

  void addListener(SLogoListener listener);

  void removeListener(SLogoListener listener);
}

interface Command {
  void execute();
}

interface SlogoListener {
  void onUpdateValue(String variableName, double value);
  void onUpdateTurtleState(ImmutableTurtle turtleState);
  void onUpdateVariable(String text, double value);
}