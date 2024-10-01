package big.manopoly;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

import big.manopoly.data.PlayerRepository;
import big.manopoly.data.CityRepository;
import big.manopoly.models.*;
import big.manopoly.utilities.UtilityName;

@SpringBootApplication
public class ManopolyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManopolyApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CityRepository repo, PlayerRepository repo2) {
		return args -> {
			// TODO finish testing
			Property utility1 = new Utility(1, UtilityName.ELECTRIC);
			Property utility2 = new Utility(1, UtilityName.WATER);

			Player player1 = new Player(new Position());
			// Player player2 = new Player(new Position());

			// player1.addProperty(utility1);
			// player1.addProperty(utility2);

			Utility utility11 = (Utility) utility1;

			utility1.getRent();
		};
	}

}
