package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.ModelState;
import slogo.model.node.Node;
import slogo.model.Turtle;

public class QuotientCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    myTurtle = null;
    ModelState model = new ModelState();
    node = new CommandNode("math.QuotientCommand", model);

  }

  @ParameterizedTest
  @CsvSource({
      "720, 2, 360",
      "-39393, -200, 196.965",
      "-8.39, 3.14, -2.67197452229",
      "0.5, -1.2, -0.41666666666",
      "7.3, -2.8, -2.60714285714",
      "2147483647.0, 2147483647.0, 1.0",
      "-2147483648.0, -2147483648.0, 1.0",
      "3.4028235E38, -3.4028235E38, -1.0",
      "1.0001, 0.0001, 10001.0",
      "360, 10, 36.0"
  })
  void testQuotientBasic(String op1, String op2, String result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1, null));
    node.addChild(new ConstantNode(op2, null));
    assertEquals(Double.parseDouble(result), node.getValue(), DELTA);
  }

  @Test
  void testDivideByZero() throws InvocationTargetException, IllegalAccessException {
    {
      node.addChild(new ConstantNode("50", null));
      node.addChild(new ConstantNode("0", null));
      Throwable e = assertThrows(ArithmeticException.class, () -> {
        node.getValue();
      });
    }

  }

}