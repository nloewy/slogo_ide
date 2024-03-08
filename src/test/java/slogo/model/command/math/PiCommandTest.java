package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().peek().add(1);
    assertEquals(Math.PI, new CommandNode("Pi", model).evaluate(),
        DELTA);
  }
}
