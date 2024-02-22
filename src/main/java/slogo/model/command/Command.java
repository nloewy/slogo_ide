package slogo.model.command;

import java.util.List;
import slogo.model.SlogoListener;

public abstract class Command {

  public abstract double execute(List<Double> arguments);

  public abstract int getNumberOfArgs();
  public void notifyListener(SlogoListener listener, double value) {
    listener.onReturn(value);
  }
}
