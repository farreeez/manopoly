package big.manopoly.models;

import jakarta.persistence.Embeddable;

// This class is created to ensure that the position of a player never goes outside the length of the board array.
@Embeddable
public class Position {
    // initial player position
    private int position;

    public Position() {
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public void add(int squares) {
        int maxPosition = 40;

        position = (position + squares) % maxPosition;
    }
}
