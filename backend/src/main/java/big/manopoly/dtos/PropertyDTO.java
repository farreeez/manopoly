package big.manopoly.dtos;

import java.util.List;

import big.manopoly.models.Colour;
import big.manopoly.utils.PropertyType;
import big.manopoly.utils.RentDisplay;

public class PropertyDTO extends BoardSquareDTO {

    private PropertyType type;
    private Boolean mortgaged;
    private Long ownerId; // Assuming you only want the ID of the owner instead of the whole Player entity
    private String ownerName; // Owner's name (can be null if not owned)
    private Colour playerColour;
    private List<RentDisplay> rentList;

    // Constructor
    public PropertyDTO(String id, int position, String name, Long boardId, int price, PropertyType type,
            Boolean mortgaged, Long ownerId,
            String ownerName, Colour playerColour, List<RentDisplay> rentList) {
        super(id, position, name, boardId, price, true);
        this.type = type;
        this.mortgaged = mortgaged;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.playerColour = playerColour;
        this.rentList = rentList;
    }

    // Getters and setters
    public List<RentDisplay> getRentList() {
        return rentList;
    }

    public void setRentList(List<RentDisplay> rentList) {
        this.rentList = rentList;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public Boolean getMortgaged() {
        return mortgaged;
    }

    public void setMortgaged(Boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setPlayerColour(Colour colour) {
        this.playerColour = colour;
    }

    public Colour getPlayerColour() {
        return playerColour;
    }
}
