package big.manopoly.models.CardTypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.models.Card;
import big.manopoly.models.Player;
import big.manopoly.utils.PropertyType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("GoToNearestCard")
// a card used for when a player has to go to the nearest kind of a property.
public class GoToNearestCard extends Card {
    @Enumerated(EnumType.STRING)
    private PropertyType targetType;

    public GoToNearestCard() {
        super();
    }

    @JsonCreator
    public GoToNearestCard(@JsonProperty("message") String message,
            @JsonProperty("targetType") PropertyType targetType) {
        super(message);
        this.message = message;
        this.targetType = targetType;
    }

    @Override
    public void action(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }

}
