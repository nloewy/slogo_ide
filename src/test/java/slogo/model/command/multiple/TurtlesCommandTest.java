package slogo.model.command.multiple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;



public class TurtlesCommandTest extends CommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;



  @Test
  void testTurtlesBasic()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    model = new ModelState();
    model.getTurtles().clear();
    model.getActiveTurtles().add(new ArrayList<>());
    model.getTurtles().put(5, new Turtle(5));
    model.getActiveTurtles().peek().add(5);
    model.getTurtles().put(4, new Turtle(4));
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().peek().add(4);
    node = new CommandNode("Turtles", model);
    assertEquals(2.0, node.evaluate(), DELTA);
  }

}
