package big.manopoly.models;

// class to be extended by all the positions on the board.
public class BoardSquare {
    private final int position;

    public BoardSquare(int position) {
        this.position = position;
    }


    public int getPosition() {
        return position;
    }
}
