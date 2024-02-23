package slogo.model.command.bool;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.Node;
import slogo.model.Turtle;

public class EqualCommandTest {

  public static final double DELTA = 0.001;
  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTurtle = null;
    node = new CommandNode("slogo.model.command.bool.EqualCommand", myTurtle);
  }

  @ParameterizedTest
  @CsvSource({
      "1, 1, 1",
      "0, 0, 1",
      "1, 0, 0",
      "0, 1, 0",
      "12345, 12345, 1",
      "12345, 54321, 0",
      "-54321, -54321, 1",
      "-54321, 54321, 0",
      "12.345, 12.345, 1",
      "12.345, 67.891, 0",
      "-12.345, -12.345, 1",
      "-12.345, 12.345, 0",
      "1E40, 1.000000000001E40, 0",
      "1E40, 1E40, 1",
  })
  void testEqual(String op1, String op2, int result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChildren(new ConstantNode(op1, myTurtle));
    node.addChildren(new ConstantNode(op2, myTurtle));
    assertEquals(result, node.getValue(), DELTA);
  }


}