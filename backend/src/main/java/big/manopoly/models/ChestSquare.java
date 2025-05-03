package big.manopoly.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ChestSquare")
public class ChestSquare extends BoardSquare {
    
    public ChestSquare() {
        super();
    }

    public ChestSquare(Board board, int position, String name) {
        super(board, position, name);
    }
}
