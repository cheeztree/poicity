package poicity.service;

import java.util.List;

import poicity.dto.LanguageTextDTO;
import poicity.entity.UserTags;

public interface LanguageTextService {
	List<LanguageTextDTO> findByLangId(Long lang_id);
}
