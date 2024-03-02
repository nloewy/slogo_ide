package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class SumCommandTest extends CommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    myTurtle = null;
    model = new ModelState();
    node = new CommandNode("math.Sum", model);

  }

  @ParameterizedTest
  @CsvSource({
      "720, 0, 720",
      "-39393, -200, -39593",
      "-8.39, 3.14, -5.25",
      "0.5, -1.2, -0.7",
      "7.3, -2.8, 4.5",
      "2147483647.0, 2147483647.0, 4294967294.0",
      "-2147483648.0, -2147483648.0, -4294967296.0",
      "3.4028235E38, -3.4028235E38, 0.0",
      "1.0001, 0.0001, 1.0002",
      "360, 10, 370"
  })
  void testSumBasic(String op1, String op2, String result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1, model));
    node.addChild(new ConstantNode(op2, model));
    assertEquals(Double.parseDouble(result), node.evaluate(), DELTA);
  }

}