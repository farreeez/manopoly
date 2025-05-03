package big.manopoly.models.CardTypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.models.Card;
import big.manopoly.models.Player;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PayMoneyPerHouseCard")
// a card for when the player has to pay for the number of houses and hotels that they own.
public class PayMoneyPerHouseCard extends Card {
    private int moneyOwed;
    private int costPerHouse;
    private int costPerHotel;

    public PayMoneyPerHouseCard() {
        super();
    }

    @JsonCreator
    public PayMoneyPerHouseCard(@JsonProperty("message") String message,
            @JsonProperty("moneyOwed") int moneyOwed,
            @JsonProperty("costPerHouse") int costPerHouse,
            @JsonProperty("costPerHotel") int costPerHotel) {
        super(message);
        this.message = message;
        this.moneyOwed = moneyOwed;
        this.costPerHotel = costPerHotel;
        this.costPerHouse = costPerHouse;
    }

    @Override
    public void action(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }

}
