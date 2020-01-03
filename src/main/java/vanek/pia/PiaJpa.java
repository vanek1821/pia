package vanek.pia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import vanek.pia.configuration.AppConfig;
import vanek.pia.configuration.DbConfig;

@SpringBootApplication
@Import({
	AppConfig.class,
	DbConfig.class
})
public class PiaJpa {

	public static void main(String[] args) {
		SpringApplication.run(PiaJpa.class, args);
	}

}
