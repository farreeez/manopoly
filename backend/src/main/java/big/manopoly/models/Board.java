package big.manopoly.models;

import big.manopoly.data.BoardRepository;
import big.manopoly.services.BoardSubscriptionManager;
import big.manopoly.utils.CityName;
import big.manopoly.utils.PlayerColour;
import big.manopoly.utils.PropertyType;
import big.manopoly.utils.TrainName;
import big.manopoly.utils.UtilityName;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Player> players = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BoardSquare> squares;

    private int currentTurn;

    private boolean diceRolled;

    private int doubleCount;

    @ElementCollection
    private int[] diceRolls;

    @Embedded
    private CardPile cardPile;
    
    public Board() {
        diceRolled = false;
        currentTurn = 0;
        doubleCount = 0;

        possibleColours = Arrays.asList(PlayerColour.values());
        diceRolls = new int[2];

        cardPile = new CardPile(); 
    }

    @PostPersist
    public void initialiseSquares() {
        if (squares != null) {
            return;
        }

        squares = Arrays.asList(
                new NotProperty(this, 0, "GO"), // GO
                new City(this, 1, PropertyType.BROWN, CityName.Cairo), // Brown 1
                new ChestSquare(this, 2, "Community Chest"), // Community Chest
                new City(this, 3, PropertyType.BROWN, CityName.Alexandria), // Brown 2
                new IncomeTax(this, 4, "Income Tax", true), // Income Tax
                new Train(this, 5, TrainName.Grand_Central_Terminal), // Train 1
                new City(this, 6, PropertyType.LIGHT_BLUE, CityName.Athens), // Light Blue 1
                new ChanceSquare(this, 7, "Chance"), // Chance
                new City(this, 8, PropertyType.LIGHT_BLUE, CityName.Mykonos), // Light Blue 2
                new City(this, 9, PropertyType.LIGHT_BLUE, CityName.Olympia), // Light Blue 3
                new NotProperty(this, 10, "Jail"), // Jail
                new City(this, 11, PropertyType.PINK, CityName.Kyoto), // Pink 1
                new Utility(this, 12, UtilityName.ELECTRIC), // Electric Company
                new City(this, 13, PropertyType.PINK, CityName.Osaka), // Pink 2
                new City(this, 14, PropertyType.PINK, CityName.Tokyo), // Pink 3
                new Train(this, 15, TrainName.Tokyo_Station), // Train 2
                new City(this, 16, PropertyType.ORANGE, CityName.Texas), // Orange 1
                new ChestSquare(this, 17, "Community Chest"), // Community Chest
                new City(this, 18, PropertyType.ORANGE, CityName.Washington), // Orange 2
                new City(this, 19, PropertyType.ORANGE, CityName.California), // Orange 3
                new NotProperty(this, 20, "Free Parking"), // Free Parking
                new City(this, 21, PropertyType.RED, CityName.Shenzhen), // Red 1
                new ChanceSquare(this, 22, "Chance"), // Chance
                new City(this, 23, PropertyType.RED, CityName.Beijing), // Red 2
                new City(this, 24, PropertyType.RED, CityName.Shanghai), // Red 3
                new Train(this, 25, TrainName.Berlin_Hauptbahnhof), // Train 3
                new City(this, 26, PropertyType.YELLOW, CityName.Brisbane), // Yellow 1
                new City(this, 27, PropertyType.YELLOW, CityName.Melbourne), // Yellow 2 (fixed spelling)
                new Utility(this, 28, UtilityName.WATER), // Water Works
                new City(this, 29, PropertyType.YELLOW, CityName.Sydney), // Yellow 3
                new Jail(this, 30, "Go to Jail"), // Go to Jail
                new City(this, 31, PropertyType.GREEN, CityName.Christchurch), // Green 1
                new City(this, 32, PropertyType.GREEN, CityName.Wellington), // Green 2
                new ChestSquare(this, 33, "Community Chest"), // Community Chest
                new City(this, 34, PropertyType.GREEN, CityName.Auckland), // Green 3
                new Train(this, 35, TrainName.Gare_du_Nord), // Train 4
                new ChanceSquare(this, 36, "Chance"), // Chance
                new City(this, 37, PropertyType.DARK_BLUE, CityName.Stockholm), // Dark Blue 1
                new IncomeTax(this, 38, "Luxury Tax", false), // Luxury Tax
                new City(this, 39, PropertyType.DARK_BLUE, CityName.Malmö) // Dark Blue 2
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

    public int[] getDiceRolls() {
        return diceRolls;
    }

    public Player getPlayerWithCurrentTurn() {
        if (players == null || players.isEmpty()) {
            return null;
        }

        return players.get(currentTurn);
    }

    public BoardSquare getBoardSquare(Position position) {
        return squares.get(position.getPosition());
    }

    public BoardSquare getBoardSquare(int position) {
        return squares.get(position);
    }

    public CardPile getCardPile() {
        return cardPile;
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

            if (currentTurn > players.size() - 1) {
                currentTurn = 0;
            }

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
            player.setFree(true);
            player.setMoney(1500);
            return true;
        }
    }

    // iterates current turn to roll dice if the dice has been rolled
    public boolean endTurn() {
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

        if (player.isFree()) {
            player.getPosition().add(squaresMoved);

            // allows player to keep rolling if there is a double
            // TODO: handle triple doubles.
            diceRolled = !(firstDice == secondDice);

            if (firstDice == secondDice) {
                doubleCount++;

                if (doubleCount >= 3) {
                    player.setFree(false);
                    doubleCount = 0;
                }

            } else {
                doubleCount = 0;
            }
        } else {
            player.setJailCounter(player.getJailCounter() + 1);

            if (firstDice == secondDice) {
                player.setFree(true);
                player.resetJailCounter();

                player.getPosition().add(squaresMoved);

            } else {
                doubleCount = 0;
            }

            if (player.getJailCounter() >= 3) {
                player.setFree(true);
                player.resetJailCounter();

                doubleCount = 0;
            }
        }

        diceRolls[0] = firstDice;
        diceRolls[1] = secondDice;

        return diceRolls;
    }

    public void saveBoard(BoardRepository boardRepository, boolean rollDiceAction) {
        boardRepository.save(this);

        BoardSubscriptionManager.instance().processSubsFor(this.id, boardRepository, rollDiceAction, -1);
    }

}
