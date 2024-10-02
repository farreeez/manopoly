package big.manopoly.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.utilities.PropertyType;
import jakarta.persistence.*;;

// all entity types are put into a single table and dtype is used to see the
// type
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class Property extends BoardSquare {
    @Id
    protected String id;

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
    public Property(@JsonProperty("position") int position, @JsonProperty("type") PropertyType type, @JsonProperty("name") String name) {
        super(position);
        this.type = type;
        // after the : the board id is inputed to identify different properties with the same name across different boards
        this.id = name + ":";
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

    public String getId() {
        return id;
    }

    public String getName() {
        return id.split(":")[0];
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
