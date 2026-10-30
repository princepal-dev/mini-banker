import config.AppConfig;
import controller.AuthController;
import controller.HealthController;
import database.DatabaseConnection;
import server.Server;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;

public class Main {
  public static void main(String[] args) throws IOException {
    // Getting our app config
    AppConfig config = AppConfig.getConfig();

    // Getting the DB connection
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();

    // Getting the server
    HttpServer server = Server.getServer(config.getAddress(), config.getPort(), 0);

    // Routers
    server.createContext("/auth", AuthController.getController());
    server.createContext("/health", HealthController.getController());
  
    // Threading env and start
    server.setExecutor(null);
    
    // Starting the server
    server.start();

    // Printing server is running
    System.out.println("Server running on PORT :" + config.getPort());
  }
}
