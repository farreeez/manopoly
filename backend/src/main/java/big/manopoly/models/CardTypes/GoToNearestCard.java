package big.manopoly.models.CardTypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.models.Card;
import big.manopoly.models.Player;
import big.manopoly.utils.PropertyType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class GoToNearestCard extends Card {
    @Embedded
    private PropertyType targetType;

    public GoToNearestCard() {
        super();
    }

    @JsonCreator
    public GoToNearestCard(@JsonProperty("message") String message, @JsonProperty("ofTypeChance") Boolean ofTypeChance,
            @JsonProperty("targetType") PropertyType targetType) {
        super(ofTypeChance, message);
        this.ofTypeChance = ofTypeChance;
        this.message = message;
        this.targetType = targetType;
    }

    @Override
    public void action(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }

}
