package poicity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.sql.DataSource;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class ConfigIni {

	private final static Path path = Paths.get(new File("").getAbsolutePath() + "/config.ini");
	
	private static Ini init() {
		String mexErrore = "Il file 'config.ini' non è stato trovato o non è formattato correttamente.\n"
				+ "Per favore, verifica la directory '" + path + "'\n\n"
				+ "File di esempio:\n"
				+ "https://politecnicobari-my.sharepoint.com/:u:/g/personal/c_llovera_studenti_poliba_it/EW5RQ7dUGopIpLSlnOTcTnEB3jRwFGlpLne96OwG4U3nug?e=B0mu3F";
		
		Ini ini = null;
		try {
			ini = new Ini(path.toFile());
		} catch (IOException e) {
			System.err.println(mexErrore);
			System.exit(1);
		}

		if (ini == null) {
			System.err.println(mexErrore);
			System.exit(1);
		}
		
		return ini;
	}
	
	public static HashMap<String, String> loadConfigIni(String targetSectionName) {
		Ini ini = init();
		
		HashMap<String, String> map = new HashMap<>();

		for (String sectionName : ini.keySet()) {
			Section section = ini.get(sectionName);
			if (sectionName.equals(targetSectionName)) {
				for (String optionKey : section.keySet()) {
//					System.out.println(optionKey+": "+section.get(optionKey));
					map.put(optionKey, section.get(optionKey));
				}
			}
		}
		
		if(map.isEmpty()) {
			System.err.println("Nel file 'config.ini' non c'è la sezione '" + targetSectionName + "' oppure questa è vuota.");
		}
		
		return map;
	}
	
    @Bean
    public DataSource getDataSource() {
    	HashMap<String, String> map = loadConfigIni("dataSource");
    	
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(map.get("spring.datasource.driverClassName"));
        dataSourceBuilder.url(map.get("spring.datasourc.url"));
        dataSourceBuilder.username(map.get("spring.datasource.username"));
        dataSourceBuilder.password(map.get("spring.datasource.password"));
        
        return dataSourceBuilder.build();
    }
	
	public static String OnlineLinkLogo() {
    	HashMap<String, String> map = loadConfigIni("onlineLinks");
    	String linkDefaultAvatar = map.get("logo");
    	
    	return linkDefaultAvatar;
	}
    
	public static String OnlineLinkDefaultAvatar() {
    	HashMap<String, String> map = loadConfigIni("onlineLinks");
    	String linkDefaultAvatar = map.get("defaultAvatar");
    	
    	return linkDefaultAvatar;
	}
	
	
	

}
