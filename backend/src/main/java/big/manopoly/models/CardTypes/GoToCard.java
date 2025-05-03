package big.manopoly.models.CardTypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.models.Card;
import big.manopoly.models.Player;
import big.manopoly.models.Position;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class GoToCard extends Card {
    @Embedded
    private Position targetSquare;

    public GoToCard () {
        super();
    }

    @JsonCreator
    public GoToCard (@JsonProperty("message") String message, @JsonProperty("ofTypeChance") Boolean ofTypeChance, @JsonProperty("target") Position target) {
        super(ofTypeChance, message);
        this.ofTypeChance = ofTypeChance;
        this.message = message;
        this.targetSquare = target;
    }

    @Override
    public void action(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }

}
