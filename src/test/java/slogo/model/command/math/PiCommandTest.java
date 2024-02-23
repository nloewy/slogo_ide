package slogo.model.command.math;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
public class PiCommandTest {

  public static final double DELTA = .001;

  @Test
  void testPi()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    assertEquals(Math.PI, new CommandNode("slogo.model.command.math.PiCommand", null).getValue(), DELTA);
  }
}
