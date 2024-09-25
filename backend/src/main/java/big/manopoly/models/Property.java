package big.manopoly.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public abstract class Property extends BoardSquare {

    @Id
    // this should be the name of the property hyphen board id with the board being the board that the property belongs to (e.g. "name-board.id").
    // this is because if there are multiple games there will be duplicate properties.
    private String name;
    private int price;

    // null if owned by the bank.
    private Player owner;

    // TODO check if not needed once functionality done
    private Board board;

    // number of houses built on the property (5 if property has hotel).
    private int houses;

    // money given to the player when property is mortgaged.
    private int mortgagePayout;

    // money player has to pay to demortgage.
    private int mortgageCost;
    private boolean mortgaged;

    public Property(int position) {
        super(position);
    } 

    // getters with no setters.
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Board getBoard() {
        return board;
    }

    public int getMortgagePayout() {
        return mortgagePayout;
    }

    public int getMortgageCost() {
        return mortgageCost;
    }

    // getters with setters.
    public int getHouses() {
        return houses;
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public Player getOwner() {
        return owner;
    }

    // getters that calculate a variable output
    public abstract int getHouseCost();

    public abstract int getRent();


    // setters
    public void setHouses(int houses) {
        this.houses = houses;
    }

    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
}
