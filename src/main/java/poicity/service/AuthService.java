package poicity.service;

import poicity.dto.AuthResponse;
import poicity.dto.LoginDTO;
import poicity.dto.UserDTO;
import poicity.entity.User;


public interface AuthService {

	AuthResponse login(LoginDTO request);
	AuthResponse register(UserDTO request);
	AuthResponse registerFromGoogle(User user);

}
