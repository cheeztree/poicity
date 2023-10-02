package poicity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import poicity.dto.UserTagsDTO;
import poicity.entity.Language;
import poicity.entity.LanguageText;
import poicity.entity.UserTags;

@Repository
public interface LanguageTextRepository extends JpaRepository<LanguageText, Long>{
	
	List<LanguageText> findByLangId(long id);
	List<LanguageText> findAllByLangIdAndUserTagsIdIn(long id_lang, List<Long> id_userTags);
	List<LanguageText> findAllByUserTagsId(long id);
//	List<LanguageText> findAllByIdElementAndLanguage(String id_element, Language language);
	List<LanguageText> findAllByLang(Language lang);
	List<LanguageText> findByIdElement(String id_element);
	List<LanguageText> findByIdElementAndLang(String id_element, Language lang);

//	@Query(value="  SELECT *\r\n"
//			+ "  FROM lang_text\r\n"
//			+ "  WHERE id_element = 'pref'\r\n"
//			+ "  AND id_lang = :id_lang", nativeQuery=true)
//	List<LanguageText> findByLanguage(@Param("id_lang") long id_lang);
	
//	List<LanguageText> findByLanguage(@Param("id_lang") long id_lang);

	
//	@Query(value="  SELECT l\r\n"
//			+ "  FROM LanguageText l\r\n"
//			+ "  WHERE l.id_element = 'pref'\r\n"
//			+ "  AND l.lang.id = 1")
//	List<LanguageText> findAllByIdElementAnd();
}
