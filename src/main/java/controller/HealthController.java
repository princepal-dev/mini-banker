package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HealthController implements HttpHandler {
  static HealthController controller = null;

  private HealthController() {}

  public static HealthController getController() {
    if (controller == null) controller = new HealthController();
    return controller;
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String response = "Hello from raw Java!";

    exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");

    exchange.sendResponseHeaders(200, response.getBytes().length);

    try (OutputStream outputStream = exchange.getResponseBody()) {
      outputStream.write(response.getBytes());
    }
  }
}
