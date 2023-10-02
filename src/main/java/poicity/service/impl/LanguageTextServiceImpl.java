package poicity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poicity.dto.LanguageTextDTO;
import poicity.entity.LanguageText;
import poicity.mapper.MyMapper;
import poicity.repository.LanguageTextRepository;
import poicity.service.LanguageTextService;

@Service
public class LanguageTextServiceImpl implements LanguageTextService{

	@Autowired
	MyMapper mapper;
	@Autowired
	LanguageTextRepository langTextRepo;
	
	@Override
	public List<LanguageTextDTO> findByLangId(Long lang_id) {
		List<LanguageText> listAll = langTextRepo.findByLangId(lang_id);
		
		List<LanguageTextDTO> listDTO = new ArrayList<>();
		for(LanguageText langText : listAll) {
//			listDTO.add(mapper.langTextToLangTextDTO(langText));
			LanguageTextDTO ltDTO = new LanguageTextDTO();
			ltDTO.setId_element(langText.getIdElement());
			ltDTO.setText(langText.getText());
			
			listDTO.add(ltDTO);
		}
		
		return listDTO;
	}

}
