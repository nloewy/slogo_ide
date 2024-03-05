package slogo.model.command.multiple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.ListNode;
import slogo.model.node.Node;

public class TellCommandTest extends CommandTest {
  public static final double DELTA = 0.001;
  private ModelState model;
  private Node node;
  @Test
  void testTurtlesBasic()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    model = new ModelState();
    model.getTurtles().clear();
    model.getTurtles().put(5, new Turtle(5));
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().get(0).add(5);
    model.getTurtles().put(4, new Turtle(4));
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().get(0).add(4);
    model.getTurtles().put(10, new Turtle(10));
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().get(0).add(10);
    model.getTurtles().put(9, new Turtle(9));
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().get(0).add(9);
    node = new CommandNode("multiple.Tell", model);
    node.addListener(myListener);
    Node nodeChild = new ListNode("[", model) ;
    Node nodeTwo = new CommandNode("math.Sum", model) ;
    Node nodeThree = new ConstantNode("4", model) ;
    Node nodeFour = new ConstantNode("1", model) ;
    Node nodeFive = new ConstantNode("9", model) ;
    Node nodeSix = new ConstantNode("2", model) ;
    node.getChildren().add(nodeChild);
    nodeChild.getChildren().add(nodeTwo);
    nodeTwo.getChildren().add(nodeThree);
    nodeTwo.getChildren().add(nodeFour);
    nodeChild.getChildren().add(nodeFive);
    nodeChild.getChildren().add(nodeSix);
    assertEquals(2.0, node.evaluate(), DELTA);
    assertEquals(3.0, model.getActiveTurtles().peek().size(), DELTA);
    assertEquals(5.0, model.getTurtles().size(), DELTA);
    assertTrue(model.getActiveTurtles().peek().contains(2));
    assertTrue(model.getActiveTurtles().peek().contains(9));
    assertTrue(model.getActiveTurtles().peek().contains(5));
  }
}
