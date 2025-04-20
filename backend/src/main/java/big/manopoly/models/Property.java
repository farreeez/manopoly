package big.manopoly.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.utils.PropertyType;
import big.manopoly.utils.RentDisplay;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;

// all entity types are put into a single table and dtype is used to see the
// type
@Entity
@DiscriminatorValue("PROPERTY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Property Type", discriminatorType = DiscriminatorType.STRING)
public abstract class Property extends BoardSquare {
    @Enumerated(EnumType.STRING)
    protected PropertyType type;

    // null if owned by the bank.
    @ManyToOne(cascade = CascadeType.PERSIST)
    protected Player owner;

    protected boolean mortgaged;

    public Property() {
        super();
    }

    @JsonCreator
    public Property(@JsonProperty("board") Board board ,@JsonProperty("position") int position, @JsonProperty("type") PropertyType type, @JsonProperty("name") String name) {
        super(board, position, name);
        this.type = type;
        // after the : the board id is inputed to identify different properties with the same name across different boards
        this.id = name + ":" + board.getId();
    }

    // getters with setters.
    public boolean isMortgaged() {
        return mortgaged;
    }

    public Player getOwner() {
        return owner;
    }

    public PropertyType getType() {
        return type;
    }

    public String getName() {
        return id.split(":")[0];
    }

    // getters that calculate a variable output
    public abstract int getRent() throws Exception;

    public abstract List<RentDisplay> getPossibleRents();

    @Override
    public abstract int getPrice();

    public abstract int getMortgageCost();

    public abstract int getMortgagePayout();

    // setters
    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

}
