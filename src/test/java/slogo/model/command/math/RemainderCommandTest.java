package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

public class RemainderCommandTest extends CommandTest {

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
    node = new CommandNode("math.Remainder", model);

  }

  @ParameterizedTest
  @CsvSource({
      "720, 2, 0",
      "-39393, -200, -193",
      "-8.39, 3.14, -2.11",
      "0.5, -1.2, 0.5",
      "7.3, -2.8, 1.7",
      "2147483647.0, 2147483647.0, 0.0",
      "-2147483648.0, -2147483648.0, 0.0",
      "3.4028235E38, -3.4028235E38, 0.0",
      "1.0001, 0.0001, 0.0001",
      "360, 10, 0"
  })
  void testRemainderBasic(String op1, String op2, String result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1,model));
    node.addChild(new ConstantNode(op2,model));
    assertEquals(Double.parseDouble(result), node.evaluate(), DELTA);
  }

  @Test
  void testRemainderDivideByZero() throws InvocationTargetException, IllegalAccessException {
    {
      node.addChild(new ConstantNode("50",model));
      node.addChild(new ConstantNode("0",model));
      assertThrows(InvalidOperandException.class, () -> {
        node.evaluate();
      });
    }

  }

}
