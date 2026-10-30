package utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpUtil {
  private HttpUtil() {
    throw new UnsupportedOperationException();
  }

  public static void sendJson(HttpExchange exchange, int statusCode, Object res)
      throws IOException {
    byte[] responseBytes = JsonUtil.toBytes(res);

    exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

    exchange.sendResponseHeaders(statusCode, responseBytes.length);

    try (OutputStream outputStream = exchange.getResponseBody()) {
      outputStream.write(responseBytes);
    }
  }
}
