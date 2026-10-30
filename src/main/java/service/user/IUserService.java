package service.user;

import dto.user.LoginRequest;
import dto.user.RegisterRequest;
import dto.user.LoginResponse;

public interface IUserService {
    void register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
