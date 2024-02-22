package slogo.mathutils;

public class MathUtils {
  public static double toDegrees(double radians) {
    return radians * 180 / Math.PI;
  }

  public static double toRadians(double degrees) {
    return degrees * Math.PI / 180;
  }
}
