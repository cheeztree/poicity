package poicity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import poicity.entity.PointOfInterest;

@Repository
public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Long> {

	List<PointOfInterest> findAllByCityId(long city_id);
	
}
