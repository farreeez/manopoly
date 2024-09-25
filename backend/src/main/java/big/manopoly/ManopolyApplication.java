package big.manopoly;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

import big.manopoly.data.PlayerRepository;
import big.manopoly.models.*;

@SpringBootApplication
public class ManopolyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManopolyApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PlayerRepository repo) {
		return args -> {
			Position pos = new Position();
			repo.save(new Player(pos));
		};
	}

}
