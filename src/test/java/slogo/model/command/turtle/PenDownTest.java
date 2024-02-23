package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.Node;
import slogo.model.Turtle;

public class PenDownTest {
  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTurtle = new Turtle(1);
    node = new CommandNode("slogo.model.command.turtle.PenDownCommand", myTurtle);
  }

  @Test
  void testBasicPenDown()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setPen(false);
    Assertions.assertEquals(1, node.getValue());
    Assertions.assertTrue(myTurtle.getPen());
  }

  @Test
  void testPenDownAlreadyDown()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setPen(true);
    Assertions.assertEquals(1, node.getValue());
    Assertions.assertTrue(myTurtle.getPen());
  }
}

