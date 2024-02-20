public interface ModelExternal {
  public void parse(String commandStr);
  public File loadXml(String path);
  public File saveXml(String path);
  public void resetModel();
}
