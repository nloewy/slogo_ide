package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;

public class MinusCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    myTurtle = null;
    ModelState model = new ModelState();
    node = new CommandNode("slogo.model.command.math.MinusCommand", model);

  }

  @ParameterizedTest
  @CsvSource({
      "720, -720",
      "-39393, 39393",
      "2.67197452229, -2.67197452229",
      "0.000001, -0.000001",
      "2147483647.0, -2147483647.0",
      "-2147483648.0, 2147483648.0",
      "3.4028235E38, -3.4028235E38, -1.0",
      "1.0001, -1.0001",
      "0, 0"
  })
  void testMinusBasic(String op1, String result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1, null));
    assertEquals(Double.parseDouble(result), node.getValue(), DELTA);
  }

}