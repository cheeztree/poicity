package poicity.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poicity.repository.PointOfInterestImageRepository;
import poicity.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{

	String path = "C:\\Users\\Telsone\\Desktop\\imgPetruzzelli\\";

	@Autowired
	PointOfInterestImageRepository poiImgRepo;
	
	@Override
	public InputStream getPoiImgById(long id_poi_img) {
		InputStream is = null;
		try {
			is = new FileInputStream(poiImgRepo.findById(id_poi_img).get().getPathImgPoi());
//			is.close();
		} catch (Exception e) {
//			e.printStackTrace();
			System.err.println("Image not found");
		}
		return is;
	}
	
	@Override
	public InputStream getResource(String fileName) {
		
//		try {
//			InputStream is = new FileInputStream(fullPath);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		
		InputStream is = null;
		try {
			is = new FileInputStream(path + fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return is;
	}

	
	
}
