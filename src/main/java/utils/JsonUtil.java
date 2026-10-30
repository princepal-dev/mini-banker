package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtil {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  static {
    MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  private JsonUtil() {
    throw new UnsupportedOperationException();
  }

  public static <T> T fromStream(InputStream inputStream, Class<T> clazz) {
    if (inputStream == null) return null;
    try {
      return MAPPER.readValue(inputStream, clazz);
    } catch (IOException e) {
      throw new RuntimeException("Failed to parse HTTP request payload");
    }
  }

  public static byte[] toBytes(Object obj) {
    try {
      return MAPPER.writeValueAsBytes(obj);
    } catch (IOException e) {
      throw new RuntimeException("Failed to serialize JSON", e);
    }
  }
}
