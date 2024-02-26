package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import slogo.model.command.CommandTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class RandomCommandTest extends CommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTurtle = null;
    ModelState model = new ModelState();
    node = new CommandNode("math.Random", model, myListener);

  }

  @ParameterizedTest
  @CsvSource({
      "839.3333333",
      "0.44444",
      "0.0000000000000001",
      "1",
      "0200"
  })
  void testBasicRandom(String positiveValue)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(positiveValue, null, myListener));
    double val = node.getValue();
    assertTrue(0 <= val);
    assertTrue(Double.parseDouble(positiveValue) > val);

  }

  @Test
  void testRandomZero()
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode("0.00000", null, myListener));
    assertEquals(0, node.getValue(), DELTA);
  }

  @Test
  void testRandomNegative()
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode("-90.00000", null, myListener));
    Throwable e = assertThrows(InvocationTargetException.class, () -> {node.getValue();});
    assertInstanceOf(IllegalArgumentException.class, e.getCause());
  }
}