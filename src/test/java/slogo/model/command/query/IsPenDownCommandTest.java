package slogo.model.command.query;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.node.CommandNode;
import slogo.model.node.Node;

public class IsPenDownCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    ModelState model = new ModelState();
    model.getTurtles().add(new Turtle(1));
    myTurtle = model.getTurtles().get(0);
    node = new CommandNode("query.IsPenDownCommand", model);
  }

  @Test
  void testPenDown()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setPen(false);
    Assertions.assertEquals(0, node.getValue());
    Assertions.assertFalse(myTurtle.getPen());
  }

  @Test
  void testPenUp()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setPen(true);
    Assertions.assertEquals(1, node.getValue());
    Assertions.assertTrue(myTurtle.getPen());
  }
}

