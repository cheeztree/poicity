package poicity.service;

import java.util.List;

import poicity.dto.LanguageDTO;
import poicity.entity.Language;

public interface LanguageService {
	List<Language> findAll();
	List<LanguageDTO> getOnlyActive();
	boolean existsById(Long id);
}
