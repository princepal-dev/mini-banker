package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class Server {
  static HttpServer server = null;

  private Server() {}

  public static HttpServer getServer(String addr, int port, int backlog) throws IOException {
    if (server == null) {
      server = HttpServer.create(new InetSocketAddress(addr, port), backlog);
    }
    return server;
  }
}
