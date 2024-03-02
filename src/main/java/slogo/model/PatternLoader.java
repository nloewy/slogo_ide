package slogo.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PatternLoader {

  private final Properties properties;

  public PatternLoader(String filePath) throws IOException {
    properties = new Properties();
    FileInputStream inputStream = new FileInputStream(filePath);
    properties.load(inputStream);
    inputStream.close();
  }

  public String getPattern(String key) {
    return properties.getProperty(key);
  }
}
