package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ModelState;
import slogo.model.api.Node;
import slogo.model.Turtle;

public class PenDownCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    ModelState model = new ModelState();
    model.getTurtles().add(new Turtle(1));
    myTurtle = model.getTurtles().get(0);
    node = new CommandNode("turtle.PenDownCommand", model);
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

