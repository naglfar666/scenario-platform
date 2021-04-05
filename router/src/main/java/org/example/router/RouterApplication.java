package org.example.router;

import org.example.config.kafka.KafkaGeneralConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({KafkaGeneralConfiguration.class})
public class RouterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouterApplication.class, args);
	}

}
