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

public class ProductCommandTest extends CommandTest {

  public static final double DELTA = 0.1;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    myTurtle = null;
    ModelState model = new ModelState();
    node = new CommandNode("math.Product", model);

  }

  @ParameterizedTest
  @CsvSource({
      "720, 0, 0",
      "-39393, -200, 7878600",
      "-8.39, 3.14, -26.3346",
      "0.5, -1.2, -0.6",
      "7.3, -2.8, -20.44",
      "1.0001, 0.0001, 0.00010001",
      "360, 10, 3600"
  })
  void testProductBasic(String op1, String op2, String result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1, new ModelState()));
    node.addChild(new ConstantNode(op2, new ModelState()));
    assertEquals(Double.parseDouble(result), node.evaluate(), DELTA);
  }

}