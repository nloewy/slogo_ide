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

public class NotCommandTest {

  public static final double DELTA = 0.001;
  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTurtle = null;
    ModelState model = new ModelState();
    node = new CommandNode("slogo.model.command.bool.NotCommand", model);
  }

  @ParameterizedTest
  @CsvSource({
      "0, 1",
      "0.0000000, 1",
      "00000.0000, 1",
      "1, 0",
      "0.00000000000002, 0",
      "-340404, 0",
      "23568765435, 0",
      "3.14, 0"
  })
  void testNot(String op1, int result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChildren(new ConstantNode(op1, myTurtle));
    assertEquals(result, node.getValue(), DELTA);
  }
}
