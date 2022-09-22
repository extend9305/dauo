package tech.exam.dauo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import tech.exam.dauo.config.ConfigProperties;

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class DauoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DauoApplication.class, args);
	}

}
