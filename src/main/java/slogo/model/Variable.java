package slogo.model;

public class Variable {

  private String myName;
  private double myValue;

  public Variable(String name) {
    this(name, 0.0);
  }

  public Variable(String name, double value) {
    myName = name;
    myValue = value;
  }

  public String getName() {
    return myName;
  }

  public double getValue() {
    return myValue;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (getClass() != other.getClass()) {
      return false;
    }
    Variable other1 = (Variable) other;
    return (other1.getName().equals(myName));
  }
}
