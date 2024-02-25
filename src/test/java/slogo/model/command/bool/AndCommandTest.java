package slogo.model.command.bool;

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

public class AndCommandTest {

  public static final double DELTA = 0.001;
  private Turtle myTurtle;
  private Node node;
  private ModelState model;


  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTurtle = null;
    model = new ModelState();
    node = new CommandNode("slogo.model.command.bool.AndCommand", model);

  }

  @ParameterizedTest
  @CsvSource({
      "1, 2, 1",
      "0, 0, 0",
      "0.000000, 0000000, 0",
      "0, 0.0000, 0",
      "-1, 1, 1",
      "1, -1, 1",
      "1.5, 2.5, 1",
      "0, 2.2220003, 0",
      "0, -2.2221, 0",
      "-2.2221, 0, 0",
      "2.2220003, 0, 0",
      "1E40, 1.000000000001E40, 1",
      "1E40, 1E40, 1",
      "1E-40, 1E-41, 1",
      "1E-41, 1E-40, 1",
      "-1E-61, -1E-62, 1",
      "-1E-62, -1E-61, 1",
      "-1E-62, -1E-62, 1"

  })
  void testAnd(String op1, String op2, int result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1, model));
    node.addChild(new ConstantNode(op2, model));
    assertEquals(result, node.getValue(), DELTA);
  }
}
