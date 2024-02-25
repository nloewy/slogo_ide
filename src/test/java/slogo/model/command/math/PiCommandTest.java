package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ModelState;

public class PiCommandTest {

  public static final double DELTA = .001;

  @Test
  void testPi()

      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    ModelState model = new ModelState();
    assertEquals(Math.PI, new CommandNode("math.PiCommand", model).getValue(),
        DELTA);
  }
}
