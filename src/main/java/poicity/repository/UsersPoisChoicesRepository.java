package poicity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import poicity.entity.UsersPoisChoices;

@Repository
public interface UsersPoisChoicesRepository extends JpaRepository<UsersPoisChoices, Long>{
	
	
//	@Modifying
//	@Query(
//			  value = "INSERT INTO UsersPoisChoices (poi_id, poi_choices_id, user_id)\r\n"
//			  		+ "VALUES (:poi_id, :poi_choices, :user_id)"
//			  , nativeQuery = true
//			  )
//	void saveNewUpc(long poi_id, long poi_choices, long user_id);
	
//	void saveUsersPoisChoices(UsersPoisChoices upc);

}
