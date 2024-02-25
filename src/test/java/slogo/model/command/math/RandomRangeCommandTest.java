package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;

public class RandomRangeCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    myTurtle = null;
    ModelState model = new ModelState();
    node = new CommandNode("slogo.model.command.math.RandomRangeCommand", model);

  }

  @ParameterizedTest
  @CsvSource({
      "839.3333333, 1000",
      "0, 0.0000000000000001",
      "-1, 1",
      "0200, 3000"
  })
  void testBasicRandomRange(String min, String max)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(min, null));
    node.addChild(new ConstantNode(max, null));
    double val = node.getValue();
    assertTrue(Double.parseDouble(min) <= val);
    assertTrue(Double.parseDouble(max) >= val);

  }

  @Test
  void testRandomRangeSameVal()
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode("0.00000", null));
    node.addChild(new ConstantNode("0", null));
    assertEquals(0, node.getValue(), DELTA);
  }

  @Test
  void testRandomRangeIllegal()
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode("-90.00000", null));
    node.addChild(new ConstantNode("-90.00100", null));
    Throwable e = assertThrows(IllegalArgumentException.class, () -> {
      node.getValue();
    });
  }
}