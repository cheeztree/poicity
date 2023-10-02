package poicity.service;

import java.util.List;

import poicity.dto.UserDTO;
import poicity.entity.User;

public interface UserService {
	User findById(long id);
	User updateUser(UserDTO userDTO);
	void saveUser(User user);
	User findByEmail(String email);
	User save(User user);
	boolean existsByEmail(String email);
	List<UserDTO> findAll();
}
