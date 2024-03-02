package slogo.model.command.control;


import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;
import slogo.model.node.VariableNode;

public class MakeCommandTest extends CommandTest {


  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;

  @Test
  public void testMakeCommand()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    model.getTurtles().add(new Turtle(1));
    node = new CommandNode("control.Make", model, myListener);
    Node variableNode = new VariableNode("MyVar", model, myListener);
    node.addChild(variableNode);
    node.addChild(new ConstantNode("5", model, myListener));
    Assertions.assertEquals(5, node.getValue());
    Assertions.assertEquals(5.0, model.getVariables().get("myvar"));
  }

  @Test
  public void testMakeCommandWithExpression()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    model.getTurtles().add(new Turtle(1));
    node = new CommandNode("control.Make", model, myListener);
    Node variableNode = new VariableNode("Var", model, myListener);
    Node nodeTwo = new CommandNode("math.Sum", model, myListener);
    Node nodeThree = new ConstantNode("5", model, myListener);
    Node nodeFour = new ConstantNode("7", model, myListener);
    node.addChild(variableNode);
    node.addChild(nodeTwo);
    nodeTwo.addChild(nodeThree);
    nodeTwo.addChild(nodeFour);
    Assertions.assertEquals(12, node.getValue());
    Assertions.assertEquals(12.0, model.getVariables().get("var"));
  }

}
