package slogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.api.Model;
import slogo.model.api.TurtleRecord;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class ParseTest {


  public static final double DELTA = .0001;
  private Turtle myTurtle;
  private ModelState model;
  private SlogoModel slogo;

  @BeforeEach
  void setUp() {

    slogo = new SlogoModel(new SlogoListener() {
      @Override
      public void onUpdateValue(String variableName, Number newValue) {
      }

      @Override
      public void onUpdateTurtleState(TurtleRecord turtleState) {
      }

      @Override
      public void onResetTurtle(int id) {
      }

      @Override
      public void onReturn(double value) {
      }
    });

  }

@Test
  void testCommandZero()
    throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("PENUP");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertFalse(myTurtle.getPen());
  }


  @Test
  void testNestedIfElseFalse()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("PENUP FORWARD #LOL IFELSE PENDOWN? SUM 50 30 PRODUCT 30 SUM 1 1 RT 50");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertFalse(myTurtle.getPen());
    assertEquals(60.0, myTurtle.getY(), DELTA);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testNestedIfElseTrue()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("PENDOWN #LOL FORWARD IFELSE PENDOWNP SUM 50 30 PRODUCT 30 SUM 1 1 RT 50");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertTrue(myTurtle.getPen());
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
    assertEquals(80.0, myTurtle.getY(), DELTA);

  }
  @Test
  void testNestedIfElseList()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("[ [ [ [ [ PENDOWN #LOL FORWARD IFELSE PENDOWNP SUM 50 30 PRODUCT 30 SUM 1 1 RT 50 ] ] ] ] ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertTrue(myTurtle.getPen());
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
    assertEquals(80.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testVariables()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("MAKE :CLASS 308 FORWARD SUM :CLASS 192");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(500.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testRepeat()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("MAKE :CLASS 10 REPEAT DIFFERENCE :CLASS 5 [ RIGHT :CLASS ] RIGHT 50");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals( 100.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testCaseSensitiveCommands()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("mAkE :CLASS 10 REPEAT dIffEreNcE :CLASS 5 [ RIGHT :CLASS ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testCaseSensitiveVariables()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("mAkE :CLASS 10 REPEAT dIffEreNcE :CLass 5 [ RIGHT :cLASS ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }
  @Test
  void testRandom()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("rt 270 mAke :random sum 1 random 100 fd sum 1 :random");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(0.0, myTurtle.getY(), DELTA);
    assertTrue(myTurtle.getX() <= -1 && myTurtle.getX() >= -101);
    assertEquals(270.0, myTurtle.getHeading(), DELTA);
  }
  @Test
  void testDash()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("set :count 12 set :distance 20 to dash [ ] [ repeat :count [ pu fd :distance pd fd :distance ] ] dash");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertTrue( myTurtle.getPen());
    assertEquals(12.0, slogo.getModelstate().getVariables().get(":count"));
    assertEquals(20.0, slogo.getModelstate().getVariables().get(":distance"));

    assertEquals(480.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testDashWithoutUserDefined()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("repeat 12 [ fd 20 fd 20 ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertTrue( myTurtle.getPen());
    assertEquals(480.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testVariableMaker()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("set :count 16");
    assertEquals(16.0, slogo.getModelstate().getVariables().get(":count"));
    slogo.parse("pendown");
    assertTrue(slogo.getModelstate().getTurtles().get(0).getPen());
    slogo.parse("set :count ifelse pendown? power :count 2 sqrt :count");
    assertEquals(256.0, slogo.getModelstate().getVariables().get(":count"));
    slogo.parse("set :count 16");
    slogo.parse("penup");
    slogo.parse("set :count ifelse pendown? power :count 2 sqrt :count");
    assertEquals(4.0, slogo.getModelstate().getVariables().get(":count"));
  }


}