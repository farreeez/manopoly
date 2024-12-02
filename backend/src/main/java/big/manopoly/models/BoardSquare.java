package big.manopoly.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
// import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.ManyToOne;

// class to be extended by all the positions on the board.
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Board Square Type", discriminatorType = DiscriminatorType.STRING)
public class BoardSquare {
    @Id
    protected String id;

    private int position;

    protected String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    protected Board board;

    public BoardSquare() {
        position = 0;
        this.board = null;
    }

    @JsonCreator
    public BoardSquare(@JsonProperty("board") Board board, @JsonProperty("position") int position, @JsonProperty("name") String name) {
        this.position = position;
        this.board = board;
        this.id = position + ":" + board.getId();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public Board getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return 0;
    }
}
