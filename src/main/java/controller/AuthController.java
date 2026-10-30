package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import common.CommonResponse;
import dto.user.LoginRequest;
import dto.user.RegisterRequest;
import dto.user.LoginResponse;
import service.user.IUserService;
import service.user.UserService;
import utils.HttpUtil;
import utils.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class AuthController implements HttpHandler {
  static AuthController controller = null;

  private final IUserService userService;

  private AuthController() {
    this.userService = new UserService();
  }

  public static AuthController getController() {
    if (controller == null) controller = new AuthController();
    return controller;
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String method = exchange.getRequestMethod();
    String path = exchange.getRequestURI().getPath();

    switch (method) {
      case "POST" -> {
        if (path.equals("/login")) {
          login(exchange);
        } else if (path.equals("/register")) {
          register(exchange);
        } else {
          methodNotAllowed(exchange);
        }
      }
      default -> methodNotAllowed(exchange);
    }
  }

  private void login(HttpExchange exchange) throws IOException {
    InputStream inputStream = exchange.getRequestBody();

    if (inputStream == null) {
      sendError(exchange, 400, "Request body is empty");
      return;
    }

    LoginRequest request = JsonUtil.fromStream(inputStream, LoginRequest.class);
    LoginResponse response = userService.login(request);
    HttpUtil.sendJson(exchange, 200, response);
  }

  private void register(HttpExchange exchange) throws IOException {
    InputStream inputStream = exchange.getRequestBody();

    if (inputStream == null) {
      sendError(exchange, 400, "Request body is empty");
      return;
    }

    RegisterRequest request = JsonUtil.fromStream(inputStream, RegisterRequest.class);
    userService.register(request);
    HttpUtil.sendJson(exchange, 201, CommonResponse.success("User registered successfully!"));
  }

  private void methodNotAllowed(HttpExchange exchange) throws IOException {
    sendError(exchange, 405, "Method not allowed");
  }

  private void sendError(HttpExchange exchange, int statusCode, String message) throws IOException {
    HttpUtil.sendJson(exchange, statusCode, Map.of("status", "error", "message", message));
  }
}
