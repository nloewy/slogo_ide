package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;

public class PowerCommandTest {

  public static final double DELTA = 0.1;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    myTurtle = null;
    ModelState model = new ModelState();
    node = new CommandNode("slogo.model.command.math.PowerCommand", model);

  }

  @ParameterizedTest
  @CsvSource({
      "2, 3, 8",
      "2.5, 1.5, 3.95284708",
      "2, -3, .125",
      "0, 20000.4433, 0",
      "-20000.4433, 0, 1",
      "-493944.349403244, 1, -493944.349403244",
      "-1, 1000000.00000, 1",
      "10, 40, 1E40",
  })
  void testPowerBasic(String op1, String op2, String result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChildren(new ConstantNode(op1, myTurtle));
    node.addChildren(new ConstantNode(op2, myTurtle));
    assertEquals(Double.parseDouble(result), node.getValue(), DELTA);
  }


  @ParameterizedTest
  @CsvSource({
      "-5, -1.5",
      "0, -1",
      "0, -3.55393922292929",
      "-302.235, -103.75"
  })
  void testPowerUndefined(String op1, String op2)
      throws InvocationTargetException, IllegalAccessException {
    node.addChildren(new ConstantNode(op1, myTurtle));
    node.addChildren(new ConstantNode(op2, myTurtle));
    Throwable e = assertThrows(InvocationTargetException.class, () -> {
      node.getValue();
    });
    assertInstanceOf(IllegalArgumentException.class, e.getCause());
  }
}
