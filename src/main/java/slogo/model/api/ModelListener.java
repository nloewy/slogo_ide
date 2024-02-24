package slogo.model.api;
import java.util.function.Function;
import slogo.model.ModelState;

public interface ModelListener {

  double execute(Function<ModelState, Double> action);
}



