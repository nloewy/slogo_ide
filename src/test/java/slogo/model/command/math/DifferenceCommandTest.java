package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.api.Node;
import slogo.model.Turtle;

public class DifferenceCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    myTurtle = null;
    ModelState model = new ModelState();
    node = new CommandNode("math.DifferenceCommand", model);

  }

  @ParameterizedTest
  @CsvSource({
      "720, 0, 720",
      "-39393, -200, -39193",
      "-8.39, 3.14, -11.53",
      "0.5, -1.2, 1.7",
      "7.3, -2.8, 10.1",
      "2147483647.0, 2147483647.0, 0.0",
      "-2147483648.0, -2147483648.0, 0.0",
      "3.4028235E38, -3.4028235E38, 6.805647E38",
      "1.0001, 0.0001, 1.0",
      "360, 10, 350"
  })
  void testDifferenceBasic(String op1, String op2, String result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1, null));
    node.addChild(new ConstantNode(op2, null));
    assertEquals(Double.parseDouble(result), node.getValue(), DELTA);
  }

}