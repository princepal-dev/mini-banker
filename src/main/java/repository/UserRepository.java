package repository;

import model.User;

import java.sql.ResultSet;
import java.util.Optional;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import database.DatabaseConnection;

public class UserRepository implements IUserRepository {
  private static IUserRepository userRepository = null;
  private final DatabaseConnection connection;

  private UserRepository() {
    this.connection = DatabaseConnection.getInstance();
  }
  
  public static IUserRepository getUserRepository() {
    if (userRepository == null)
      userRepository = new UserRepository();
    return userRepository;
  }

  @Override
  public void save(User user) {
    String sql = "INSERT INTO users (user_name, email, password) VALUES (?, ?, ?)";

    try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)) {
      statement.setString(1, user.getUserName());
      statement.setString(2, user.getEmail());
      statement.setString(3, user.getPassword());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Failed to save user", e);
    }
  }

  @Override
  public Optional<User> findById(Long id) {
    String sql = "SELECT * FROM users WHERE id = ?";

    try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)) {
      statement.setLong(1, id);
      Optional<User> user = getUser(statement);
      if (user != null) return user;
    } catch (SQLException e) {
      throw new RuntimeException("Failed to find user by ID", e);
    }
    return Optional.empty();
  }

  @Override
  public boolean existsByEmail(String email) {
    String sql = "SELECT * FROM users WHERE email = ?";

    try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)) {
      statement.setString(1, email);
      ResultSet set = statement.executeQuery();
      
      if (set.next()) return true;
    } catch (SQLException e) {
      throw new RuntimeException("Failed to find user by ID", e);
    }
    
    return false;
  }

  @Override
  public boolean existsByUsername(String username) {
    return false;
  }

  @Override
  public Optional<User> findByEmail(String email) {
    String sql = "SELECT * FROM users WHERE email = ?";

    try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)) {
      statement.setString(1, email);
      Optional<User> user = getUser(statement);
      if (user != null) return user;
    } catch (SQLException e) {
      throw new RuntimeException("Failed to find user by ID", e);
    }

    return Optional.empty();
  }

  private Optional<User> getUser(PreparedStatement statement) throws SQLException {
    ResultSet resultSet = statement.executeQuery();

    if (resultSet.next()) {
      User user = new User();
      user.setId(resultSet.getLong("id"));
      user.setUserName(resultSet.getString("user_name"));
      user.setEmail(resultSet.getString("email"));
      user.setPassword(resultSet.getString("password"));
      return Optional.of(user);
    }
    return null;
  }
}
