package brian.camel.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "brian.camel")
public class CamelServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelServiceApplication.class, args);
	}

}
