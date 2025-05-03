package big.manopoly.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ChanceSquare")
public class ChanceSquare extends BoardSquare {
    
    public ChanceSquare() {
        super();
    }

    public ChanceSquare(Board board, int position, String name) {
        super(board, position, name);
    }
}
