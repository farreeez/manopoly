package big.manopoly.dtos;

import big.manopoly.models.Colour;
import big.manopoly.models.Position;
import java.util.Set;

public class PlayerDTO {
    private Long id;
    private String name;
    private Colour colour;
    private Position position;  // You can choose to include Position details as needed
    private Long boardId;  // Instead of including the full Board, just include the ID for simplicity
    private Integer money;
    private Set<PropertyDTO> properties;  // Set of properties the player owns
    private Boolean free;

    // Constructor that takes all the necessary fields
    public PlayerDTO(Long id, String name, Colour colour, Position position, Long boardId, Integer money, Set<PropertyDTO> properties, Boolean free) {
        this.id = id;
        this.name = name;
        this.colour = colour;
        this.position = position;
        this.boardId = boardId;
        this.money = money;
        this.properties = properties;
        this.free = free;
    }

    // Getters and setters for all the fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Set<PropertyDTO> getProperties() {
        return properties;
    }

    public void setProperties(Set<PropertyDTO> properties) {
        this.properties = properties;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }
}
