package slogo.view;

public class ComboChoice {

  private String name;
  private final String value;

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
