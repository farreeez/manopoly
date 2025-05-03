package big.manopoly.models.CardTypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.models.Card;
import big.manopoly.models.Player;
import jakarta.persistence.Embeddable;

@Embeddable
public class GetOutOfJailCard extends Card {

    public GetOutOfJailCard() {
        super();
    }

    @JsonCreator
    public GetOutOfJailCard(@JsonProperty("message") String message,
            @JsonProperty("ofTypeChance") Boolean ofTypeChance) {
        super(ofTypeChance, message);
        this.ofTypeChance = ofTypeChance;
        this.message = message;
    }

    @Override
    public void action(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }

}
