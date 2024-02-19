public interface ModelExternal {


  public void parseCommandString(String commandStr);

  public CommandData getCommandData();

  public void updateModel(CommandData commandData);

  public File saveXml(String path);

  public void reset();

}