package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.Node;
import slogo.model.Turtle;

public class RandomCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    myTurtle = null;
    node = new CommandNode("slogo.model.command.math.RandomCommand", myTurtle);

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
    Random random = new Random();
    node.addChildren(new ConstantNode(positiveValue, myTurtle));
    double val = node.getValue();
    assertTrue(0 <= val);
    assertTrue(Double.parseDouble(positiveValue) > val);

  }

  @Test
  void testRandomZero()
      throws InvocationTargetException, IllegalAccessException {
    node.addChildren(new ConstantNode("0.00000", myTurtle));
    assertEquals(0, node.getValue(), DELTA);
  }

  @Test
  void testRandomNegative()
      throws InvocationTargetException, IllegalAccessException {
    node.addChildren(new ConstantNode("-90.00000", myTurtle));
    Throwable e = assertThrows(InvocationTargetException.class, () -> {node.getValue();});
    assertTrue(e.getCause() instanceof IllegalArgumentException);
  }
}