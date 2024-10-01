package big.manopoly.models;

import jakarta.persistence.MappedSuperclass;

// class to be extended by all the positions on the board.
@MappedSuperclass
public class BoardSquare {
    private final int position;

    public BoardSquare() {
        position = 0;
    }

    public BoardSquare(int position) {
        this.position = position;
    }


    public int getPosition() {
        return position;
    }
}
