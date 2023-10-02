package poicity.config;

import java.util.Arrays;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


//import org.springframework.security.core.userdetails.User;
//import poicity.entity.User;

import lombok.RequiredArgsConstructor;
import poicity.mapper.MyMapper;
import poicity.service.CustomUserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public MyMapper model() {
		return new MyMapper();
	}
	
	@Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
	
	@Bean
	public AuthenticationProvider authenticationProvicer() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEndecoder());
		return authenticationProvider;
	}
	
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }
	
	@Bean
	public CorsFilter corsFilter() {
		
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    final CorsConfiguration config = new CorsConfiguration();
	    
//	    config.setAllowedOrigins(Collections.singletonList("*"));
//	    config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//	    config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
	    config.setAllowedOriginPatterns(Arrays.asList("*"));
	    config.setAllowedHeaders(Arrays.asList("*"));
//	    config.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
//	    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
	    config.setAllowedMethods(Arrays.asList("*"));
	    config.setAllowCredentials(true);

	    source.registerCorsConfiguration("/**", config);
	    
	    return new CorsFilter(source);
	    
	}

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//	  web.ignoring().requestMatchers("/static/**");
//	}
//	  @Bean
//	  public CorsFilter corsFilter() {
//
//	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    CorsConfiguration config = new CorsConfiguration();
//	    config.setAllowCredentials(true); //updated to false
//	    config.addAllowedOrigin("*");
//	    config.addAllowedHeader("*");
//	    config.addAllowedMethod("GET");
//	    config.addAllowedMethod("PUT");
//	    config.addAllowedMethod("POST");
//	    source.registerCorsConfiguration("/**", config);
//	    
//	    return new CorsFilter(source);
//	  }

//	  @Bean
//	  public WebMvcConfigurer corsConfigurer() {
//	    return new WebMvcConfigurerAdapter() {
//	      @Override
//	      public void addCorsMappings(CorsRegistry registry) {
//	        registry.addMapping("/").allowedOrigins("http://localhost:3000");
//	      }
//	    };
//	  }
// 
	  
//	@Bean
//	public WebFluxConfigurer corsMappingConfigurer() {
//	    return new WebFluxConfigurer() {
//	        @Override
//	        public void addCorsMappings(CorsRegistry registry) {
//	        	WebConfigProperties.Cors cors = WebConfigProperties.getCors();
//	            registry.addMapping("/**")
//	              .allowedOrigins(cors.getAllowedOrigins())
//	              .allowedMethods(cors.getAllowedMethods())
//	              .maxAge(cors.getMaxAge())
//	              .allowedHeaders(cors.getAllowedHeaders())
//	              .exposedHeaders(cors.getExposedHeaders());
//	        }
//	    };
//	}
    public static String bCrypt(String data) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        return passwordEncoder.encode(data);
    }
	
	@Bean
	public PasswordEncoder passwordEndecoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> customUserDetailsService.loadUserByEmail(email);
//        return username -> customUserDetailsService.loadUserByUsername(username);
    }

}
