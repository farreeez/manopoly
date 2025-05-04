package big.manopoly.models.CardTypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.models.Card;
import big.manopoly.models.Player;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GoToJailCard")
public class GoToJailCard extends Card {

    public GoToJailCard() {
        super();
    }

    @JsonCreator
    public GoToJailCard(@JsonProperty("message") String message) {
        super(message);
        this.message = message;
    }

    @Override
    public void action(Player player) {
        player.setFree(false);
        player.resetJailCounter();
        player.getPosition().setPosition(10);
    }
}
