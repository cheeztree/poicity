package poicity;

import java.util.HashMap;
import java.util.Properties;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Main extends SpringBootServletInitializer  {

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(SpringCpanelApplication.class);
//	}
	
	public static void main(String[] args) {
		Properties props = getInit();

		new SpringApplicationBuilder(Main.class).properties(props).run(args);

		System.out.println(" /**********************************************\\");
		System.out.println("| ----------------SERVER AVVIATO---------------- |");
		System.out.println(" \\**********************************************/");
	}

	private static Properties getInit() {
		Properties props = new Properties();
		HashMap<String, String> mapConfig = ConfigIni.loadConfigIni("applicationProperties");

		for (String chiave : mapConfig.keySet()) {
			props.put(chiave, mapConfig.get(chiave));
		}

		return props;
	}

//	public static void main(String[] args) {
//		SpringApplication.run(Main.class, args);
//		
//		System.out.println(" /**********************************************\\");
//		System.out.println("| ----------------SERVER AVVIATO---------------- |");
//		System.out.println(" \\**********************************************/");
//	}

//	@Bean
//	public ModelMapper modelMapper(){
//		return new ModelMapper();
//	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/greeting-javaconfig").allowedOrigins();
//			}
//		};
//	}

//	public Docket apis() {
//		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("poicity")).build();
//	}

}
