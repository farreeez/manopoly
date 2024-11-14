package big.manopoly;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

import big.manopoly.data.PlayerRepository;
import big.manopoly.data.TrainRepository;
import big.manopoly.data.UtilityRepository;
import big.manopoly.data.BoardRepository;
import big.manopoly.data.CityRepository;
import big.manopoly.models.*;
import big.manopoly.utilities.CityName;
import big.manopoly.utilities.PropertyType;
import big.manopoly.utilities.TrainName;
import big.manopoly.utilities.UtilityName;

@SpringBootApplication
public class ManopolyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManopolyApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(BoardRepository repo, PlayerRepository repo2) {
		return args -> {
			Board board = new Board();
			repo.save(board);

			List<BoardSquare> squares = board.getSquares();
		};
	}
}
