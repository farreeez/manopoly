package big.manopoly.models.CardTypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.models.Card;
import big.manopoly.models.Player;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GoToCard")
// a card for when a player is instructed to go to a certain property or square.
public class GoToCard extends Card {
    private Integer targetSquare;

    public GoToCard() {
        super();
    }

    @JsonCreator
    public GoToCard(@JsonProperty("message") String message, @JsonProperty("target") Integer target) {
        super(message);
        this.message = message;
        this.targetSquare = target;
    }

    @Override
    public void action(Player player) {
        int currentPlayerPosition = player.getPosition().getPosition();
        if (targetSquare >= currentPlayerPosition) {
            player.getPosition().add(targetSquare - currentPlayerPosition);
            return;
        }

        int difference = 40 - currentPlayerPosition + targetSquare;

        player.getPosition().add(difference);
    }

}
