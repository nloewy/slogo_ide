package slogo.view;

public class ComboChoice {

  private final String value;
  private String name;

  public ComboChoice(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return name;
  }
}
