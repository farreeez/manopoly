package big.manopoly.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

// This class is created to ensure that the position of a player never goes outside the length of the board array.
@Embeddable
public class Position {
    // initial player position
    private int position;
    
    // max position of the monopoly board.
    @Transient
    private int maxSquare;

    public Position() {
        maxSquare = 40;
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public void add(int squares) {
        position = (position + squares) % maxSquare;
    }
}
