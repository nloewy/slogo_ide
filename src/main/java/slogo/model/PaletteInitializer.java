package slogo.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Utility class for initializing a color palette.
 *
 * @author Noah Loewy
 */
public class PaletteInitializer {

  /**
   * Initializes a color palette with predefined colors.
   *
   * @return A map representing the color palette, where the key is the color index and the value is
   * a list of RGB values.
   */

  public Map<Integer, List<Integer>> makePalette(String filename) {
    Map<Integer, List<Integer>> colors = new TreeMap<>();
    Properties properties = new Properties();
    try {
      FileInputStream fis = new FileInputStream(filename);
      properties.load(fis);
      int index = 1;
      String colorValue;
      while ((colorValue = properties.getProperty("" + index)) != null) {
        colors.put(index, parseRgb(colorValue));
        index++;
      }
    } catch (IOException e) {
      colors.put(1, Arrays.asList(0, 0, 0));
    }
    return colors;
  }

  private List<Integer> parseRgb(String colorValue) {
    String[] parts = colorValue.split(",");
    if (parts.length != 3) {
      throw new IllegalArgumentException("Invalid RGB value: " + colorValue);
    } //TODO Catch
    List<Integer> rgb = new ArrayList<>();
    for (String part : parts) {
      rgb.add(Integer.parseInt(part.trim()));
    }
    return rgb;
  }
}
