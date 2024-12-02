package big.manopoly.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// TODO jail, suprise, chance, taxes, vacation/parking,
@Entity
@DiscriminatorValue("Not a Property")
public class NotProperty extends BoardSquare {

    public NotProperty() {
        super();
    }

    public NotProperty(Board board, int position, String name) {
        super(board, position, name);
    }
}
