package slogo.model.command.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;
import slogo.model.VariableNode;

public class RepeatCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;

  @BeforeEach
  void setUp() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    model.getTurtles().add(new Turtle(1));
    node = new VariableNode("Skeeyee", model);

  }

  @Test
  void testRepeatForward() throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    //String numTimes = "5";
    //node = new CommandNode("slogo.model.command.control.RepeatCommand", model);
    //Node fwdNode = new CommandNode("slogo.model.command.turtle.ForwardCommand", model);
    //fwdNode.addChildren(new ConstantNode("50", myTurtle));
    //node.addChildren(new ConstantNode("5", myTurtle));
    //node.addChildren(fwdNode);
    //assertEquals(node.getValue(), 50, DELTA);
    //assertEquals(myTurtle.getY(), 250, DELTA);
  }
}
