package poicity.service;

import java.io.InputStream;

public interface ImageService {

	InputStream getResource(String fileName);
	InputStream getPoiImgById(long id_poi_img);

}
