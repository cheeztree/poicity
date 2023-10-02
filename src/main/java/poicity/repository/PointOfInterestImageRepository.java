package poicity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import poicity.entity.PointOfInterestImage;

@Repository
public interface PointOfInterestImageRepository extends JpaRepository<PointOfInterestImage, Long>{

}
