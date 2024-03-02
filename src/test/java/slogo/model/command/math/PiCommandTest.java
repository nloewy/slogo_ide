package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;
import slogo.model.ModelState;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;

public class PiCommandTest extends CommandTest {

  public static final double DELTA = .001;

  @Test
  void testPi()

      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    ModelState model = new ModelState();
    assertEquals(Math.PI, new CommandNode("math.Pi", model, myListener).getValue(),
        DELTA);
  }
}
