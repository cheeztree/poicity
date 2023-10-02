package poicity.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import poicity.dto.ErrorDTO;
import poicity.dto.LanguageDTO;
import poicity.dto.LanguageTextDTO;
import poicity.entity.Language;
import poicity.service.LanguageService;
import poicity.service.LanguageTextService;

@RestController
@RequestMapping("/lang")
@RequiredArgsConstructor
public class LanguageController {

//	@Autowired
//	LanguageRepository langRepo; 

	@Autowired
	private LanguageService langService;
	
	@Autowired
	private LanguageTextService langTextService;

//    @CrossOrigin
	@GetMapping("getAll")
	public ResponseEntity<List<Language>> getAll() {
		List<Language> listaLangs = langService.findAll();

		return new ResponseEntity<>(listaLangs, HttpStatus.OK);
	}

//	@CrossOrigin(exposedHeaders = {"Access-Control-Allow-Origin"})
	@GetMapping("getOnlyActive")
	public ResponseEntity<List<LanguageDTO>> getAll2() {
		System.out.println("CIAO");
		return new ResponseEntity<>(langService.getOnlyActive(), HttpStatus.OK);
	}
	
	@GetMapping("getLang/{id}")
	public ResponseEntity<Object> getLang(@PathVariable("id") Long id) {
		if(langService.existsById(id)) {
			return new ResponseEntity<>(langTextService.findByLangId(id), HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(new ErrorDTO(new Date(), "Language with id " + id + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
	}
	
//	@GetMapping("getLangFlag/{id}")
//	public ResponseEntity<Object> getLangFlag(@PathVariable("id") Long id) {
//		if(langService.existsById(id)) {
//			return new ResponseEntity<>(langTextService.findByLangId(id), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<Object>(new ErrorDTO(new Date(), "Language with id " + id + " doesn't exists."), HttpStatus.NOT_FOUND);
//		}
//	}

}
