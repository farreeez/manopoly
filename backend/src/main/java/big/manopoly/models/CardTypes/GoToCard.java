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

        if(targetSquare < 0) {
            player.getPosition().setPosition(currentPlayerPosition - 3);
            return;
        }

        goToTargetSquare(player, currentPlayerPosition, currentPlayerPosition);
    }

    public static void goToTargetSquare(Player player, int currentPlayerPosition, int targetPosition) {
        if (targetPosition >= currentPlayerPosition) {
            player.getPosition().add(targetPosition - currentPlayerPosition);
            return;
        }

        int difference = 40 - currentPlayerPosition + targetPosition;

        player.getPosition().add(difference);
    }

}
