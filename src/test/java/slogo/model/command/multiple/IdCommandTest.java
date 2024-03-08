package slogo.model.command.multiple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.Node;


public class IdCommandTest extends CommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;


  @Test
  void testIdBasic()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    model = new ModelState();
    model.getTurtles().clear();
    model.getTurtles().put(5, new Turtle(5));
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().get(0).add(5);
    model.setCurrTurtle(5);
    node = new CommandNode("Id", model);
    assertEquals(5.0, node.evaluate(), DELTA);
  }

}
