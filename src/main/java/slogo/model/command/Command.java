package slogo.model.command;

import java.util.List;

public abstract class Command {

  public abstract double execute(List<Double> arguments);
}
