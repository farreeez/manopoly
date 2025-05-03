package big.manopoly.models.CardTypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.models.Card;
import big.manopoly.models.Player;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PayMoneyCard")
// a card where the player has to pay money to either the bank or other players.
public class PayMoneyCard extends Card {
    private int moneyOwed;
    private Boolean fromOtherPlayers;

    public PayMoneyCard() {
        super();
    }

    @JsonCreator
    public PayMoneyCard(@JsonProperty("message") String message, @JsonProperty("moneyOwed") int moneyOwed, @JsonProperty("fromOtherPlayers") Boolean fromOtherPlayers) {
        super(message);
        this.message = message;
        this.moneyOwed = moneyOwed;
        this.fromOtherPlayers = fromOtherPlayers;
    }

    @Override
    public void action(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }

}
