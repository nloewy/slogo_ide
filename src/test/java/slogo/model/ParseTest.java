package slogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.api.InsufficientArgumentsException;
import slogo.model.api.InvalidCommandException;
import slogo.model.api.InvalidOperandException;
import slogo.model.api.InvalidTokenException;
import slogo.model.api.SlogoListener;
import slogo.model.api.TurtleRecord;

public class ParseTest {


  public static final double DELTA = .0001;
  private SlogoModel slogo;

  @BeforeEach
  void setUp() throws IOException {

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
      public void onReturn(double value, String string) {
      }
      @Override
      public void onCommand(String s, boolean b) {
      }
    });

  }

  @Test
  void testCommandZero()
      throws InvocationTargetException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException, InvalidCommandException {
    slogo.parse("PENUP");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertFalse(myTurtle.getPen());
  }


  @Test
  void testNestedIfElseFalse()
      throws InvocationTargetException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException, InvalidCommandException {
    slogo.parse("PENUP BK #LOL IFELSE PENDOWN? SUM 50 30 PRODUCT 30 SUM 1 1 RT 50");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertFalse(myTurtle.getPen());
    assertEquals(60.0, myTurtle.getY(), DELTA);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testNestedIfElseTrue()
      throws InvocationTargetException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException, InvalidCommandException {
    slogo.parse("PENDOWN #LOL BK IFELSE PENDOWNP SUM 50 30 PRODUCT 30 SUM 1 1 RT 50");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertTrue(myTurtle.getPen());
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
    assertEquals(80.0, myTurtle.getY(), DELTA);

  }

  @Test
  void valueWithNoCommandAlone()
      throws InvocationTargetException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException, InvalidCommandException {
    assertThrows(InvalidTokenException.class, () -> {
      slogo.parse(
          "50");
    });
  }

  @Test
  void mathErrorHalfwayThrough()
      throws InvocationTargetException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException, InvalidCommandException {
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertThrows(InvalidOperandException.class, () -> {
      slogo.parse("bk SUM 10 10 bk QUOTIENT 5 0 bk SUM 10 10");
    });
    assertEquals(20.0, myTurtle.getY(), DELTA);
  }


  @Test
  void constantWithNoCommandBetweenProperCommands()
      throws InvocationTargetException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException, InvalidCommandException {
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertThrows(InvalidTokenException.class, () -> {
      slogo.parse("FD 10 10 RT 30");
      assertEquals(10.0,myTurtle.getY(),DELTA);
    });

  }

  @Test
  void variableWithNoCommandBetweenProperCommands()
      throws InvocationTargetException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException, InvalidCommandException {
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
     assertThrows(InvalidTokenException.class, () -> {
       slogo.parse("FD 10 :C RT 30");
      assertEquals(10.0,myTurtle.getY(),DELTA);
     });

  }
  @Test
  void testNestedIfElseList()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse(
        "[ [ [ [ [ PENDOWN #LOL BK IFELSE PENDOWNP SUM 50 30 PRODUCT 30 SUM 1 1 RT 50 ] ] ] ] ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertTrue(myTurtle.getPen());
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
    assertEquals(80.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testConstantsInIfElse()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("IFELSE 1 1 1");
  }


  @Test
  void testNestedError()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertThrows(InsufficientArgumentsException.class, () -> {
      slogo.parse(
          "BK 10 REPEAT 5 [ IFELSE PENDOWN? [ FD 10 FD 10R ] FD 50 ]");
      assertEquals(-10.0, myTurtle.getY(), DELTA);
    });
  }

  @Test
  void testVariables()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("MAKE :CLASS 308 BK SUM :CLASS 192");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(500.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testRepeat()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("MAKE :CLASS 10 REPEAT DIFFERENCE :CLASS 5 [ RIGHT :CLASS ] RIGHT 50");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(100.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testInvalidCommand()
      throws InvalidCommandException, InvalidTokenException, InvocationTargetException, IllegalAccessException {
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
     assertThrows(InvalidTokenException.class, () -> {
      slogo.parse(
          "MAKE :CLASS 10 RANDOMTEXT DIFFERENCE :CLASS 5 [ RIGHT :CLASS ] RIGHT 50");
    assertEquals(10.0, slogo.getModelstate().getVariables().get(":class"), DELTA);
    assertEquals(00.0, slogo.getModelstate().getTurtles().get(0).getHeading(), DELTA);

     });
  }

  @Test
  void testInvalidToken()
      throws InvalidCommandException, InvalidTokenException, InvocationTargetException, IllegalAccessException {

    assertThrows(InvalidTokenException.class, () -> {
      slogo.parse("MAKE :CLASS 10 FD :CLaSs REPEAT DIFFERENCE :CLASS 5 [ RIGHT4 :CLASS ] RIGHT 50");
      assertEquals(10.0, slogo.getModelstate().getVariables().get(":class"), DELTA);
      assertEquals(10.0, slogo.getModelstate().getTurtles().get(0).getY(), DELTA);
      assertEquals(00.0, slogo.getModelstate().getTurtles().get(0).getHeading(), DELTA);
    });
  }


  @Test
  void testNotEnoughArguments()
      throws InsufficientArgumentsException, InvalidCommandException, InvalidTokenException {
    assertThrows(InsufficientArgumentsException.class, () -> {
      slogo.parse("MAKE :CLASS");
    });
  }

  @Test
  void testCaseSensitiveCommands()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("mAkE :CLASS 10 REPEAT dIffEreNcE :CLASS 5 [ RIGHT :CLASS ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testVariablesNotDefined()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    assertFalse(slogo.getModelstate().getVariables().containsKey(":CLASS"));
    slogo.parse("REPEAT 5 [ RIGHT NOT :CLASS ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(5.0, myTurtle.getHeading(), DELTA);
    assertEquals(0.0, slogo.getModelstate().getVariables().get(":class"), DELTA);
  }

  @Test
  void testCaseSensitiveVariables()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("mAkE :CLASS 10 REPEAT dIffEreNcE :CLass 5 [ RIGHT :cLASS ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testExtraParam()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("mAkE :CLASS 10 REPEAT dIffEreNcE :CLass 5 [ RIGHT :cLASS 10 ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testRandom()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("rt 270 mAke :random sum 1 random 100 bk sum 1 :random");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(0.0, myTurtle.getY(), DELTA);
    assertTrue(myTurtle.getX() <= -1 && myTurtle.getX() >= -101);
    assertEquals(270.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testDashWithoutUserDefined()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("repeat 12 [ bk 20 bk 20 ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertTrue(myTurtle.getPen());
    assertEquals(480.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testDashNoParams()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse(
        "set :count 12 set :distance 20 to dash [ ] [ repeat :count [ pu bk :distance pd bk :distance ] ] dash");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertTrue(myTurtle.getPen());
    assertEquals(12.0, slogo.getModelstate().getVariables().get(":count"));
    assertEquals(20.0, slogo.getModelstate().getVariables().get(":distance"));

    assertEquals(480.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testDashWithParams()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse(
        "to dash [ :count :distance ] [ repeat :count [ pu bk :distance pd bk :distance ] ] dash 12 20");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertTrue(myTurtle.getPen());
    assertEquals(480.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testDashWithStuffAfter()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse(
        "to dash [ :count :distance ] [ repeat :count [ pu bk :distance pd bk :distance ] ] dash 12 20 rt 50");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertTrue(myTurtle.getPen());
    assertEquals(480.0, myTurtle.getY(), DELTA);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testDoubleUserDefinedNoParams()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse(
        "to       partialrect [ ] [ bk 50 rt 90 bk         60 rt 90 bk 30 rt 90 ] to twice         [ ] [ repeat 2 [ partialrect ] ] twice bk 60 left 100");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertTrue(myTurtle.getPen());
    assertEquals(40.0, myTurtle.getX(), DELTA);
    assertEquals(20.0, myTurtle.getY(), DELTA);
    assertEquals(80.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testPartialFlower()
      throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvalidCommandException, InvalidTokenException {
    slogo.parse(
        "to arc [ :incr :degrees ] [ repeat quotIEnt   :degrees 2 [ bk 40 rt :incr ] ] to petal [ :size ] [ repeat 2 [ arc :size 60 rt 120 ] ] to fLOwer [ :length ] [ repeat 5 [ petal :length rt 60 ] ] flowEr 100");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertTrue(myTurtle.getPen());
    assertEquals(-34.6410161514, myTurtle.getX(), DELTA);
    assertEquals(29.0672638767, myTurtle.getY(), DELTA);
    assertEquals(180.0, myTurtle.getHeading(), DELTA);
  }


  @Test
  void testVariableMaker()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
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