package poicity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import poicity.dto.LanguageDTO;
import poicity.entity.Language;
import poicity.repository.LanguageRepository;
import poicity.repository.LanguageTextRepository;
import poicity.service.CustomUserDetailsService;
import poicity.service.JwtService;
import poicity.service.LanguageService;
import poicity.service.UserService;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	LanguageRepository langRepo;
	@Autowired
	LanguageTextRepository langTextRepo;

	@Override
	public List<Language> findAll() {
		return langRepo.findAll();
	}

	@Override
	public List<LanguageDTO> getOnlyActive() {
//		List<LanguageDTO> listLangActive = Arrays.asList(mapper.map(langRepo.findByAttivoTrue(), LanguageDTO.class));
//		System.out.println(langRepo.findByAttivoTrue());
//		List<String> list = Collections.singletonList( "data" );
//		System.out.println(listLangActive);

		List<Language> listActives = langRepo.findByAttivoTrue();

		List<LanguageDTO> listDTOs = new ArrayList<>();
		for (Language lang : listActives) {
			listDTOs.add(new LanguageDTO(lang.getId(), lang.getLanguage()));
		}

		return listDTOs;
	}
	
	@Override
	public boolean existsById(Long id) {
		return langRepo.existsById(id);
	}


}
