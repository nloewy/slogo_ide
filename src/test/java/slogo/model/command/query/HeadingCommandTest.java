package slogo.model.command.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class HeadingCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    ModelState model = new ModelState();
    model.getTurtles().add(new Turtle(1));
    myTurtle = model.getTurtles().get(0);
    node = new CommandNode("query.HeadingCommand", model);

  }

  @ParameterizedTest
  @CsvSource({
      "720",
      "-39393",
      "-8.39",
      "0.5",
      "7.3",
      "2147483647.0",
      "-2147483648.0",
      "3.4028235E38",
      "1.0001",
      "360"
  })
  void testHeadingBasic(String heading)
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setHeading(-393993);
    node.addChild(new ConstantNode(heading, null));
    assertEquals(myTurtle.getHeading(), node.getValue(), DELTA);
  }

}