package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class RandomCommandTest extends CommandTest {

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
    node = new CommandNode("Random", model);

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
    node.addChild(new ConstantNode(positiveValue, null));
    double val = node.evaluate();
    assertTrue(0 <= val);
    assertTrue(Double.parseDouble(positiveValue) > val);

  }

  @Test
  void testRandomZero()
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode("0.00000", null));
    assertEquals(0, node.evaluate(), DELTA);
  }

  @Test
  void testRandomNegative()
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode("-90.00000", null));
    assertThrows(InvalidOperandException.class, () -> {
      node.evaluate();
    });
  }
}