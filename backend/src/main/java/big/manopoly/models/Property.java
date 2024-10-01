package big.manopoly.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.utilities.PropertyType;
import jakarta.persistence.*;;

// all entity types are put into a single table and dtype is used to see the
// type
@MappedSuperclass
public abstract class Property extends BoardSquare {

    @Enumerated(EnumType.STRING)
    protected PropertyType type;

    // null if owned by the bank.
    @ManyToOne(cascade = CascadeType.PERSIST)
    protected Player owner;

    // TODO check if not needed once functionality done
    // TODO make relationship
    // protected Board board;

    protected boolean mortgaged;

    public Property() {
        super();
    }

    @JsonCreator
    //TODO include @JsonProperty("board") Board board,
    public Property(@JsonProperty("position") int position, @JsonProperty("type") PropertyType type) {
        super(position);
        this.type = type;
        // this.board = board;
    }

    // public Board getBoard() {
    // return board;
    // }

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

    // getters that calculate a variable output
    public abstract int getRent() throws Exception;

    // setters
    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

}
