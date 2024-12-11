package big.manopoly.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import big.manopoly.data.BoardRepository;
import big.manopoly.services.BoardSubscriptionManager;
import big.manopoly.utils.CityName;
import big.manopoly.utils.PlayerColour;
import big.manopoly.utils.PropertyType;
import big.manopoly.utils.TrainName;
import big.manopoly.utils.UtilityName;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<PlayerColour> takenColours = new ArrayList<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<PlayerColour> possibleColours;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Player> players = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BoardSquare> squares;

    private int currentTurn;

    private boolean diceRolled;

    public Board() {
        diceRolled = false;
        currentTurn = 0;

        possibleColours = Arrays.asList(PlayerColour.values());
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

    public int getCurrentTurn() {
        return currentTurn;
    }

    public boolean isDiceRolled() {
        return diceRolled;
    }

    public Long getId() {
        return id;
    }

    public List<PlayerColour> getPossibleColours() {
        return possibleColours;
    }

    public List<PlayerColour> getTakenColours() {
        return takenColours;
    }

    public List<BoardSquare> getSquares() {
        return squares;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayerWithCurrentTurn() {
        if(players == null || players.isEmpty()) {
            return null;
        }

        return players.get(currentTurn);
    }

    // TODO: maybe also deal with the logic of giving the player their colour and
    // taking it away in the methods below
    // TODO: also consider not throwing an error in the future and simply updating
    // the client side state such that the site doesnt crash if there is a bug.
    public void giveBackColour(PlayerColour colour) {
        boolean taken = takenColours.contains(colour);

        if (!taken) {
            throw new Error("error in method giveBackColour in Board class: colour was never taken to begin with");
        }

        takenColours.remove(colour);
    }

    public void takeColour(PlayerColour colour) {
        if (takenColours.contains(colour)) {
            throw new Error("error in method takeColour in Board class: colour already taken");
        }

        takenColours.add(colour);
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
            player.resetPosition();
            players.add(player);
            player.setFree(null);
            return true;
        }
    }

    // iterates current turn to roll dice if the dice has been rolled
    public boolean endTurn() {
        // returns false and does not iterate if the dice has not been rolled for the
        // previous turn
        if (!diceRolled) {
            return false;
        }

        currentTurn++;
        diceRolled = false;

        if (currentTurn > players.size() - 1) {
            currentTurn = 0;
        }

        return true;
    }

    public int[] movePlayer() {
        if (diceRolled) {
            return null;
        }

        Random random = new Random(System.currentTimeMillis());

        int firstDice = random.nextInt(6) + 1;
        int secondDice = random.nextInt(6) + 1;

        int squaresMoved = firstDice + secondDice;

        Player player = players.get(currentTurn);

        player.getPosition().add(squaresMoved);

        System.out.println(player.getPosition().getPosition());

        // allows player to keep rolling if there is a double
        // TODO: handle triple doubles.
        diceRolled = !(firstDice == secondDice);

        int[] diceRolls = {firstDice, secondDice};

        return diceRolls;
    }

    public void saveBoard(BoardRepository boardRepository) {
        boardRepository.save(this);

        BoardSubscriptionManager.instance().processSubsFor(this.id, boardRepository);
    }

}
