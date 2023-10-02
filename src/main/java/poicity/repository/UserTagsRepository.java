package poicity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import poicity.dto.UserTagsDTO;
import poicity.entity.UserTags;

@Repository
public interface UserTagsRepository extends JpaRepository<UserTags, Long>{
	
//	@Query(value = "SELECT id, text\r\n"
//			+ "FROM lang_text\r\n"
//			+ "WHERE id_lang = 2\r\n"
//			+ "AND id_user_tag IN (SELECT id_tag\r\n"
//			+ "FROM users_tags\r\n"
//			+ "where id_user = 1)")
//	UserTagsDTO findUserTagsByUserId();
	
	
	
}
