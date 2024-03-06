package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class MinusCommandTest extends CommandTest {

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
    node = new CommandNode("Minus", model);

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
    node.addChild(new ConstantNode(op1, model));
    assertEquals(Double.parseDouble(result), node.evaluate(), DELTA);
  }

}