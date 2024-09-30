package big.manopoly;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

import big.manopoly.data.PlayerRepository;
import big.manopoly.data.PropertyRepository;
import big.manopoly.models.*;
import big.manopoly.utilities.Colour;
import big.manopoly.utilities.PropertyName;

@SpringBootApplication
public class ManopolyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManopolyApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PropertyRepository repo, PlayerRepository repo2) {
		return args -> {
			//TODO finish testing
			Property city1 = new City(1, PropertyName.ORANGE1, Colour.ORANGE);
			Property city2 = new City(1, PropertyName.ORANGE2, Colour.ORANGE);
			Property city3 = new City(1, PropertyName.ORANGE3, Colour.ORANGE);

			repo.save(city1);
			repo.save(city2);
			repo.save(city3);

			Player player1 = new Player(new Position());
			Player player2 = new Player(new Position());

			player1.addProperty(city1);
			player1.addProperty(city2);
			player1.addProperty(city3);
		};
	}

}
