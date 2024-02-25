package slogo.model.command.control;


import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;
import slogo.model.VariableNode;

public class MakeCommandTest {


  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;


  @Test
  public void testUpdateVariableCommand()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    model.getTurtles().add(new Turtle(1));
    node = new VariableNode("Skeeyee", model);
    Node nodeTwo = new CommandNode("slogo.model.command.math.SumCommand", model);
    Node nodeThree = new ConstantNode("5", model);
    Node nodeFour = new ConstantNode("7", model);
    node.addChild(nodeTwo);
    nodeTwo.addChild(nodeThree);
    nodeTwo.addChild(nodeFour);
    Assertions.assertEquals(12, node.getValue());
    Assertions.assertEquals(12.0, model.getVariables().get("Skeeyee"));
  }

}
