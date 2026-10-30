package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import utils.HttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HealthController implements HttpHandler {
  static HealthController controller = null;

  private HealthController() {}

  public static HealthController getController() {
    if (controller == null) controller = new HealthController();
    return controller;
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String method = exchange.getRequestMethod();

    switch (method) {
      case "GET" -> getHealth(exchange);
      default -> methodNotAllowed(exchange);
    }
  }

  private void getHealth(HttpExchange exchange) throws IOException {
    Map<String, Object> response = new HashMap<>();

    response.put("status", "success");
    response.put("message", "Server is healthy");

    HttpUtil.sendJson(exchange, 200, response);
  }

  private void methodNotAllowed(HttpExchange exchange) throws IOException {
    sendError(exchange, 405, "Method not allowed");
  }

  private void sendError(HttpExchange exchange, int statusCode, String message) throws IOException {
    HttpUtil.sendJson(exchange, statusCode, Map.of("status", "error", "message", message));
  }
}
