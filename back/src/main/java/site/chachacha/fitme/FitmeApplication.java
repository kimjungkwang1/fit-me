package site.chachacha.fitme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

import static java.util.UUID.randomUUID;

@SpringBootApplication
@EnableJpaAuditing
public class FitmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitmeApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(randomUUID().toString());
	}
}
