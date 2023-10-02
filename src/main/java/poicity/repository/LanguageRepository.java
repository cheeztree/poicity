package poicity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import poicity.entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
	 List<Language> findByAttivoTrue();
	 boolean existsById(Long id);

}
