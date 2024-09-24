package big.manopoly.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class ChancePile extends BoardSquare {
    // private List<Card> chanceCards; 

    public ChancePile(int position) {
        super(position);
    }
    
}
