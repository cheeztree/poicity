package poicity.service;

import java.util.List;
import java.util.Set;

import poicity.dto.UserTagsDTO;

public interface UserTagsService {
	List<UserTagsDTO> findAll();
	void saveListUserTagsForUser(List<UserTagsDTO> list, String email);
	Set<UserTagsDTO> findByEmail(String email);
	List<UserTagsDTO> getByLang(String email);

}
