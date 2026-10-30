package repository;

import model.User;

import java.util.Optional;

public interface IUserRepository {
    void save(User user);
    Optional<User> findById(Long id);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
}
