package big.manopoly.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.utilities.PropertyName;
import jakarta.persistence.*;;

@Entity
// all entity types are put into a single table and dtype is used to see the
// type
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class Property extends BoardSquare {
    @Id
    // this should be the name of the property hyphen board id with the board being
    // the board that the property belongs to (e.g. "name-board.id").
    // this is because if there are multiple boards there will be duplicate
    // properties.
    @Enumerated(EnumType.STRING)
    protected PropertyName name;

    // null if owned by the bank.
    @ManyToOne
    protected Player owner;

    // TODO check if not needed once functionality done
    // TODO make relationship
    // protected Board board;

    protected boolean mortgaged;

    @JsonCreator
    //TODO include @JsonProperty("board") Board board,
    public Property(@JsonProperty("position") int position, @JsonProperty("name") PropertyName name) {
        super(position);
        this.name = name;
        // this.board = board;
    }

    // getters with no setters.
    public PropertyName getName() {
        return name;
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

    // getters that calculate a variable output
    public abstract int getHouseCost();

    public abstract int getRent() throws Exception;

    // setters
    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

}
