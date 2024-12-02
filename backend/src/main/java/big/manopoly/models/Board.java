package big.manopoly.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import big.manopoly.utils.CityName;
import big.manopoly.utils.PropertyType;
import big.manopoly.utils.TrainName;
import big.manopoly.utils.UtilityName;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // could use as the room code
    private Long id;

    // TODO set up other side of relationship
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Player> players = new HashSet<>();

    // TODO set up relationship + initialise
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BoardSquare> squares;

    public Board() {
    }

    @PostPersist
    public void initialiseSquares() {
        if (squares != null) {
            return;
        }

        squares = Arrays.asList(
                new NotProperty(this, 0, "GO"), // GO
                new City(this, 1, PropertyType.BROWN, CityName.BROWN1), // Brown 1
                new NotProperty(this, 2, "Community Chest"), // Community Chest
                new City(this, 3, PropertyType.BROWN, CityName.BROWN2), // Brown 2
                new NotProperty(this, 4, "Income Tax"), // Income Tax
                new Train(this, 5, TrainName.TRAIN1), // Train 1
                new City(this, 6, PropertyType.LIGHT_BLUE, CityName.LIGHT_BLUE1), // Light Blue 1
                new NotProperty(this, 7, "Chance"), // Chance
                new City(this, 8, PropertyType.LIGHT_BLUE, CityName.LIGHT_BLUE2), // Light Blue 2
                new City(this, 9, PropertyType.LIGHT_BLUE, CityName.LIGHT_BLUE3), // Light Blue 3
                new NotProperty(this, 10, "Jail"), // Jail
                new City(this, 11, PropertyType.PINK, CityName.PINK1), // Pink 1
                new Utility(this, 12, UtilityName.ELECTRIC), // Electric Company
                new City(this, 13, PropertyType.PINK, CityName.PINK2), // Pink 2
                new City(this, 14, PropertyType.PINK, CityName.PINK3), // Pink 3
                new Train(this, 15, TrainName.TRAIN2), // Train 2
                new City(this, 16, PropertyType.ORANGE, CityName.ORANGE1), // Orange 1
                new NotProperty(this, 17, "Community Chest"), // Community Chest
                new City(this, 18, PropertyType.ORANGE, CityName.ORANGE2), // Orange 2
                new City(this, 19, PropertyType.ORANGE, CityName.ORANGE3), // Orange 3
                new NotProperty(this, 20, "Free Parking"), // Free Parking
                new City(this, 21, PropertyType.RED, CityName.RED1), // Red 1
                new NotProperty(this, 22, "Chance"), // Chance
                new City(this, 23, PropertyType.RED, CityName.RED2), // Red 2
                new City(this, 24, PropertyType.RED, CityName.RED3), // Red 3
                new Train(this, 25, TrainName.TRAIN3), // Train 3
                new City(this, 26, PropertyType.YELLOW, CityName.YELLOW1), // Yellow 1
                new City(this, 27, PropertyType.YELLOW, CityName.YELLOW2), // Yellow 2
                new Utility(this, 28, UtilityName.WATER), // Water Works
                new City(this, 29, PropertyType.YELLOW, CityName.YELLOW3), // Yellow 3
                new NotProperty(this, 30, "Go to Jail"), // Go to Jail
                new City(this, 31, PropertyType.GREEN, CityName.GREEN1), // Green 1
                new City(this, 32, PropertyType.GREEN, CityName.GREEN2), // Green 2
                new NotProperty(this, 33, "Community Chest"), // Community Chest
                new City(this, 34, PropertyType.GREEN, CityName.GREEN3), // Green 3
                new Train(this, 35, TrainName.TRAIN4), // Train 4
                new NotProperty(this, 36, "Chance"), // Chance
                new City(this, 37, PropertyType.DARK_BLUE, CityName.DARK_BLUE1), // Dark Blue 1
                new NotProperty(this, 38, "Luxury Tax"), // Luxury Tax
                new City(this, 39, PropertyType.DARK_BLUE, CityName.DARK_BLUE2) // Dark Blue 2
        );
    }

    public Long getId() {
        return id;
    }

    public List<BoardSquare> getSquares() {
        return squares;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    // removes player from player pool and returns true if successful and false
    // otherwise
    public boolean removePlayer(Player player) {
        if (!players.contains(player)) {
            return false;
        } else {
            players.remove(player);
            player.setBoard(null);
            return true;
        }
    }

    // adds player and returns true if successfully added
    public boolean addPlayer(Player player) {
        if (player == null || players.contains(player)) {
            return false;
        } else {
            player.setBoard(this);
            players.add(player);
            return true;
        }
    }

}
