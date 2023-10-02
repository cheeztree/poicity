package poicity.service;

import java.util.List;

import poicity.dto.PointOfInterestDTO;
import poicity.entity.PointOfInterest;

public interface PointOfInterestService {
	
	List<PointOfInterestDTO> findByCityId(long city_id);
	PointOfInterest findById(long id);
}
