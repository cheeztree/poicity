package poicity.controller;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import poicity.dto.ErrorDTO;
import poicity.service.ImageService;
import poicity.utils.FilesUtils;

@RestController
@RequestMapping("img")
public class ImagesController {

	@Autowired
	ImageService imageService;
	
	@GetMapping(value = "/poi/{id_poi_img}"
//			, produces = "image/*"
			)
	@Async
	public ResponseEntity<?> getPoiImgById(
			@PathVariable("id_poi_img") String id_poi_img
			, HttpServletResponse response
			) {

		try {
			InputStream resource = imageService.getPoiImgById(Long.parseLong(id_poi_img));
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
			resource.close();
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Poi image with id '" + id_poi_img + "' not found.");
			return new ResponseEntity<>(new ErrorDTO("Poi image with id '" + id_poi_img + "' not found."), HttpStatus.NOT_FOUND);

		}
	
	}
	
	
	@GetMapping(value = "/logo"
//			, produces = MediaType.IMAGE_PNG_VALUE
//			, produces = "image/*" //GENERA FILE .webp
//			, produces = MediaType.ima

			)
	@Async
//	@CrossOrigin(exposedHeaders = {"Access-Control-Allow-Origin"})
	public ResponseEntity<?> getLogo() {
		File file = new File(FilesUtils.verificaEcreaPathXlogo());
		String extensionFile = FilenameUtils.getExtension(file.getPath());
//		System.out.println(extensionFile);

		String pathLogo = FilesUtils.verificaEcreaPathXlogo();
		
		try {
	        InputStream is = Files.newInputStream(Paths.get(pathLogo));

			byte[] bytes = StreamUtils.copyToByteArray(is);
			is.close();
			
			HttpHeaders responseHeaders = new HttpHeaders();
		    MediaType contentType = extensionFile.equals("jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
			responseHeaders.setContentType(contentType);
			
//			HttpHeaders responseHeaders = new HttpHeaders();
//			responseHeaders.setContentType(new MediaType("img", "png"));
			return new ResponseEntity<>(bytes, responseHeaders, HttpStatus.OK);

//			return new ResponseEntity<>(bytes, HttpStatus.OK);
		} catch (Exception e) {
//    		e.printStackTrace();

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentType(new MediaType("application", "json"));

			ResponseEntity<?> res = new ResponseEntity<>(new ErrorDTO("Image not found."), responseHeaders, HttpStatus.NOT_FOUND);
			return res;
		}
	}
}
