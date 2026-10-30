package service.user;

import dto.user.LoginRequest;
import dto.user.RegisterRequest;
import dto.user.LoginResponse;
import model.User;
import repository.IUserRepository;
import repository.UserRepository;
import utils.PasswordUtil;

public class UserService implements IUserService  {
    private final IUserRepository userRepository = UserRepository.getUserRepository();

    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) 
            throw new RuntimeException("User already exists by this email");
        
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("User already exists by this username");
        
        String hashedPassword = PasswordUtil.encodePassword(request.getPassword());

        User newUser = new User();
        newUser.setPassword(hashedPassword);
        newUser.setEmail(request.getEmail());
        newUser.setUserName(request.getUsername());
        
        userRepository.save(newUser);
    }
}
