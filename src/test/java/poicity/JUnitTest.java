package poicity;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import poicity.config.ApplicationConfig;
import poicity.config.JwtAuthenticationFilter;
import poicity.config.SecurityConfig;
import poicity.repository.LanguageTextRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest

public class JUnitTest {
	
	@Autowired
	LanguageTextRepository langTextRepo;
	
//	@Test
//	public void testfindById() {
//		long id = 35;
//		
//		assertThat(langTextRepo.findById(id));
//	}
	
	@Test
	public void testfindAll() {		
		assertThat(langTextRepo.findAll());
	}
	
}
