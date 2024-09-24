package big.manopoly.models;

import jakarta.persistence.Embeddable;

// This class is created to ensure that the position of a player never goes outside the length of the board array.
@Embeddable
public class Position {
    // initial player position
    private int position = 0;
    
    // max position of the monopoly board.
    private int maxSquare = 40;

    public Position() {}

    public int getPosition() {
        return position;
    }

    public void add(int squares) {
        position = (position + squares) % maxSquare;
    }
}
