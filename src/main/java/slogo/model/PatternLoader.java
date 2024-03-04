package slogo.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The PatternLoader class accesses and matches Strings to regex patterns obtained from a properites
 * file. The primary purpose of this class is to obtain regex patterns for token matching during
 * parsing.
 *
 * @author Noah Loewy
 */

public class PatternLoader {

  private final Properties properties;

  /**
   * Constructs a PatternLoader object with the specified properties file path.
   * @param filePath the file path of the properties file containing regex patterns
   * @throws IOException if an I/O error occurs while reading the properties file
   */

  public PatternLoader(String filePath) throws IOException {
    properties = new Properties();
    FileInputStream inputStream = new FileInputStream(filePath);
    properties.load(inputStream);
    inputStream.close();
  }

  /**
   * Retrieves the regex pattern associated with the specified key.
   * @param key the key for the regex pattern in the properties file
   * @return the regex pattern associated with the key, or null if the key is not found
   */

  public String getPattern(String key) {
    return properties.getProperty(key);
  }
}
