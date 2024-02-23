package slogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class TurtleTest {
  private Turtle myTurtle;
  @BeforeEach
  void setup () {
    myTurtle = new Turtle(1);
  }

  @Test
  void testTurtleDefaults() {
    assertTrue(myTurtle.getVisible());
    assertTrue(myTurtle.getPen());
    assertEquals(0, myTurtle.getX());
    assertEquals(0, myTurtle.getY());
    assertEquals(0, myTurtle.getHeading());
  }

  @Test
  void testImmutableTurtle() {
    myTurtle = new Turtle(1, 100, 500, false, true, 49.222);
    assertEquals(myTurtle.getX(), myTurtle.getImmutableTurtle().x());
    assertEquals(myTurtle.getY(), myTurtle.getImmutableTurtle().y());
    assertEquals(myTurtle.getPen(), myTurtle.getImmutableTurtle().pen());
    assertEquals(myTurtle.getVisible(),myTurtle.getImmutableTurtle().visible());
    assertEquals(myTurtle.getHeading(), myTurtle.getImmutableTurtle().heading());
  }

  @Test
  void testSetGetX() {
    double newX = Math.random();
    myTurtle.setX(newX);
    assertEquals(newX, myTurtle.getX());
  }

  @Test
  void testSetGetY() {
    double newY = Math.random();
    myTurtle.setY(newY);
    assertEquals(newY, myTurtle.getY());
  }

  @Test
  void testSetGetHeading() {
    double newHeading = Math.random();
    myTurtle.setX(newHeading);
    assertEquals(newHeading, myTurtle.getX());
  }

  @Test
  void testSetGetPen() {
    boolean newPen = false;
    myTurtle.setPen(newPen);
    assertFalse(myTurtle.getPen());
  }

  @Test
  void testSetGetVisible() {
    boolean newVisible = false;
    myTurtle.setVisible(newVisible);
    assertFalse(myTurtle.getVisible());
  }
}
