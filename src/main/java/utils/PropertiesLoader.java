package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
  private PropertiesLoader() {}

  public static Properties load(String filename) {
    Properties properties = new Properties();

    try (InputStream input =
        PropertiesLoader.class.getClassLoader().getResourceAsStream(filename)) {
      if (input == null) throw new IllegalArgumentException("Config file not found: " + filename);
      properties.load(input);
      return properties;
    } catch (IOException e) {
      throw new IllegalArgumentException("Config file not found: " + filename, e);
    }
  }
}
