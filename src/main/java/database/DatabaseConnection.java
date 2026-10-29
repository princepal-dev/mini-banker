package database;

import config.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
  private Connection connection;
  private static DatabaseConnection dbConnection = null;
  private final AppConfig appConfig = AppConfig.getConfig();

  private DatabaseConnection() {
    try {
      this.connection =
          DriverManager.getConnection(
              appConfig.getDbUrl(), appConfig.getDbUsername(), appConfig.getDbPassword());
      System.out.println("Database connected!");
    } catch (SQLException e) {
      throw new RuntimeException("Failed to create database connection");
    }
  }

  public static DatabaseConnection getInstance() {
    if (dbConnection == null) dbConnection = new DatabaseConnection();
    return dbConnection;
  }

  public Connection getConnection() {
    try {
      if (connection == null || connection.isClosed())
        connection =
            DriverManager.getConnection(
                appConfig.getDbUrl(), appConfig.getDbUsername(), appConfig.getDbPassword());
    } catch (SQLException e) {
      throw new RuntimeException("Failed to renew database connection");
    }
    return connection;
  }
}
