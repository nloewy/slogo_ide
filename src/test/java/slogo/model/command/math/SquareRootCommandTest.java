package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.api.Node;
import slogo.model.Turtle;

public class SquareRootCommandTest {

  public static final double DELTA = 0.1;
  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTurtle = null;
    ModelState model = new ModelState();
    node = new CommandNode("math.SquareRootCommand", model);
  }

  @ParameterizedTest
  @CsvSource({
      "0, 0",
      "36, 6",
      "121, 11",
      "1234321.00, 1111",
      "1, 1"
  })
  void testSquareRootPerfectSquares(String op1, String result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1, null));
    assertEquals(Double.parseDouble(result), node.getValue(), DELTA);
  }

  @ParameterizedTest
  @CsvSource({
      "1234567, 1111.10805427",
      "10000000, 3162.27766017",
      "98765432, 9938.08049295",
      "123456789, 11111.1110499",
      "987654321, 31426.9508477",
      "1000000000, 31622.7766017",
      "1234567890, 35136.4182864"
  })
  void testSquareRootNonPerfectSquares(String op1, String result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1, null));
    assertEquals(Double.parseDouble(result), node.getValue(), DELTA);
  }

  @ParameterizedTest
  @CsvSource({
      "2.5, 1.58113883008",
      "3.7, 1.92353840617",
      "5.8, 2.40831891576",
      "7.2, 2.68328157299",
      "11.3, 3.35559234737",
      "13.6, 3.68403149864",
      "17.9, 4.2320207939",
      "19.4, 4.40454311205",
      "23.7, 4.86929100765",
      "29.9, 5.47084861945"
  })
  void testSquareRootFloats(String op1, String result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1, null));
    assertEquals(Double.parseDouble(result), node.getValue(), DELTA);
  }

  @ParameterizedTest
  @CsvSource({
      "-.000001",
      "-3",
      "-3.85885",
      "-3939393.333",
  })
  void testSquareRootNegatives(String op1)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1, null));
    Throwable e = assertThrows(IllegalArgumentException.class, () -> {
      node.getValue();
    });
  }
}