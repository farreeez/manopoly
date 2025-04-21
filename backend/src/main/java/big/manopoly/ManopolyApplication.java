package big.manopoly;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;

@SpringBootApplication
public class ManopolyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManopolyApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(BoardRepository repo, PlayerRepository repo2) {
		return args -> {
			
		};
	}
}
