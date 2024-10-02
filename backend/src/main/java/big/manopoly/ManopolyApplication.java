package big.manopoly;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

import big.manopoly.data.PlayerRepository;
import big.manopoly.data.UtilityRepository;
import big.manopoly.data.CityRepository;
import big.manopoly.models.*;
import big.manopoly.utilities.UtilityName;

@SpringBootApplication
public class ManopolyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManopolyApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UtilityRepository repo, PlayerRepository repo2) {
		return args -> {
			// TODO finish testing
			Property utility1 = new Utility(1, UtilityName.ELECTRIC);
			Property utility2 = new Utility(1, UtilityName.WATER);

			// System.out.println(utility1.getName());

			Player player1 = new Player(new Position());
			// Player player2 = new Player(new Position());

			player1.addProperty(utility1);
			player1.addProperty(utility2);

			
			System.out.println(utility1.getRent());
			repo2.save(player1);
		};
	}

}
