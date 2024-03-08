package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.exceptions.InvalidRandomRangeInputException;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class RandomRangeCommandTest extends CommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  private ModelState model;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTurtle = null;
    model = new ModelState();
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().peek().add(1);
    node = new CommandNode("RandomRange", model);

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
    double val = node.evaluate();
    assertTrue(Double.parseDouble(min) <= val);
    assertTrue(Double.parseDouble(max) >= val);

  }

  @Test
  void testRandomRangeSameVal()
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode("0.00000", null));
    node.addChild(new ConstantNode("0", null));
    assertEquals(0, node.evaluate(), DELTA);
  }

  @Test
  void testRandomRangeIllegal()
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode("-90.00000", null));
    node.addChild(new ConstantNode("-90.00100", null));
    assertThrows(InvalidRandomRangeInputException.class, () -> {
      node.evaluate();
    });
  }
}