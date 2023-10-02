package poicity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import poicity.entity.PoiChoices;

@Repository
public interface PoiChoicesRepository extends JpaRepository<PoiChoices, Long>{
	
}
