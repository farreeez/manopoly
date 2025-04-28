package big.manopoly.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Jail")
public class Jail extends BoardSquare {

    public Jail() {
        super();
    }

    public Jail(Board board, int position, String name) {
        super(board, position, name);
    }
}
