package config;

import utils.PropertiesLoader;

import java.util.Properties;

public class AppConfig {
  private final int port;
  private final String address;
  private final String dbUrl;
  private final String dbUsername;
  private final String dbPassword;
  private static final AppConfig CONFIG = new AppConfig();

  private AppConfig() {
    Properties properties = PropertiesLoader.load("application.properties");

    this.dbUrl = properties.getProperty("db.url");
    this.dbUsername = properties.getProperty("db.username");
    this.dbPassword = properties.getProperty("db.password");
    this.address = properties.getProperty("server.address", "127.0.0.1");
    this.port = Integer.parseInt(properties.getProperty("server.port", "8080"));

    validate();
  }

  private void validate() {
    if (port < 1 || port > 65535) {
      throw new IllegalArgumentException("PORT must be between 1 and 65535");
    }

    if (address.isBlank() || dbUrl.isBlank() || dbPassword.isBlank() || dbUsername.isBlank()) {
      throw new IllegalArgumentException("Addr cannot be blank!");
    }
  }

  public static AppConfig getConfig() {
    return CONFIG;
  }

  public int getPort() {
    return port;
  }

  public String getAddress() {
    return address;
  }

  public String getDbUrl() {
    return dbUrl;
  }

  public String getDbUsername() {
    return dbUsername;
  }

  public String getDbPassword() {
    return dbPassword;
  }
}
