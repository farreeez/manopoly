package big.manopoly.models;

import java.util.*;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.services.BoardSubscriptionManager;
import big.manopoly.utils.Mapper;
import big.manopoly.utils.PlayerColour;
import big.manopoly.utils.PropertyType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PlayerColour colour;

    @Embedded
    private Position position;

    // TODO: create relationship with board once board is created
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Board board;

    private Integer money;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Property> properties = new HashSet<>();

    private Boolean free;

    @JsonCreator
    public Player(@JsonProperty("position") Position position) {
        this.position = position;
    }

    public Player() {
        this.position = new Position();
    }

    public boolean doesOwnSet(PropertyType colour) {
        List<Property> citySet = getSet(colour);

        return citySet.size() == colour.propertyCount;
    }

    public List<Property> getSet(PropertyType type) {
        List<Property> set = properties.stream().filter(p -> {
            return p.getType() == type;
        }).toList();

        return set;
    }

    public ResponseEntity<?> setColour(int colourId, BoardRepository boardRepository, PlayerRepository playerRepository) {
        if(board == null) {
            return ResponseEntity.badRequest().body("This player is not in a board and thus cannot take a colour");
        }

        PlayerColour colour;

        try {
            colour = PlayerColour.values()[colourId];
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("invalid colour id.");
        }

        if(board.getTakenColours().contains(colour)) {
            return ResponseEntity.badRequest().body("Colour is already taken.");
        }

        if(this.colour != null) {
            return ResponseEntity.badRequest().body("This player already has a colour");
        }

        this.colour = colour;

        board.getTakenColours().add(colour);

        boardRepository.save(board);
        playerRepository.save(this);

        BoardSubscriptionManager.instance().processSubsFor(board.getId(), boardRepository);

        return ResponseEntity.ok().body(Mapper.toPlayerDTO(this));
    }

    public PlayerColour getColour() {
        return colour;
    }

    public Long getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public Long getBoardId() {
        if(board == null) {
            return Long.valueOf(-1);
        } else {
            return board.getId();
        }
    }

    public Board getBoard() {
        return board;
    }

    public Integer getMoney() {
        return money;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public Boolean isFree() {
        return free;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void addProperty(Property property) {
        if (property.getOwner() != null) {
            property.getOwner().removeProperty(property);
        }

        property.setOwner(this);
        properties.add(property);
    }

    public void removeProperty(Property property) {
        property.setOwner(null);

        properties.remove(property);
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColour(PlayerColour colour) {
        this.colour = colour;
    }

    public void resetPosition() {
        this.position = new Position();
    }

}
