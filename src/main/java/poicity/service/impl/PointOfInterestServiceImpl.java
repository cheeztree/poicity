package poicity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poicity.dto.PointOfInterestDTO;
import poicity.entity.PointOfInterest;
import poicity.entity.PointOfInterestImage;
import poicity.mapper.MyMapper;
import poicity.repository.PointOfInterestRepository;
import poicity.service.PointOfInterestService;

@Service
public class PointOfInterestServiceImpl implements PointOfInterestService{

	@Autowired
	PointOfInterestRepository poiRepo;
	@Autowired
	MyMapper mapper;
	
	@Override
	public List<PointOfInterestDTO> findByCityId(long city_id) {
		List<PointOfInterest> listPoi = poiRepo.findAllByCityId(city_id);
		List<PointOfInterestDTO> listPoiDTO = new ArrayList<PointOfInterestDTO>();
		
		for(PointOfInterest poi : listPoi) {
			listPoiDTO.add(mapper.poiToPoiDTO(poi));
		}
		
		return listPoiDTO;
	}

	@Override
	public PointOfInterest findById(long id) {
		return poiRepo.findById(id).get();
	}

}
