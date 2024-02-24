package slogo.model.command.query;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;

public class IsShowingCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    ModelState model = new ModelState();
    model.getTurtles().add(new Turtle(1));
    myTurtle = model.getTurtles().get(0);
    node = new CommandNode("slogo.model.command.query.IsShowingCommand", model);
  }

  @Test
  void testinVisible()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setVisible(false);
    Assertions.assertEquals(0, node.getValue());
    Assertions.assertFalse(myTurtle.getVisible());
  }

  @Test
  void testVisible()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setVisible(true);
    Assertions.assertEquals(1, node.getValue());
    Assertions.assertTrue(myTurtle.getVisible());
  }
}

