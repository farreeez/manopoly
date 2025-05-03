package big.manopoly.models.CardTypes;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.models.Card;
import big.manopoly.models.City;
import big.manopoly.models.Player;
import big.manopoly.models.Property;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PayMoneyPerHouseCard")
// a card for when the player has to pay for the number of houses and hotels that they own.
public class PayMoneyPerHouseCard extends Card {
    private int costPerHouse;
    private int costPerHotel;

    public PayMoneyPerHouseCard() {
        super();
    }

    @JsonCreator
    public PayMoneyPerHouseCard(@JsonProperty("message") String message,
            @JsonProperty("costPerHouse") int costPerHouse,
            @JsonProperty("costPerHotel") int costPerHotel) {
        super(message);
        this.message = message;
        this.costPerHotel = costPerHotel;
        this.costPerHouse = costPerHouse;
    }

    @Override
    public void action(Player player, BoardRepository boardRepository, PlayerRepository playerRepository) {
        List<Property> properties = new ArrayList<>(player.getProperties());

        int houseCount = 0;
        int hotelCount = 0;

        for(int i = 0; i < properties.size(); i++ ) {
            Property property = properties.get(i);

            City city = (City) Hibernate.unproxy(property);

            int houseNumber = city.getHouses();

            if(houseNumber == 5) {
                hotelCount++;
            } else {
                houseCount++;
            }
        }

        int cost = houseCount * costPerHouse + hotelCount * costPerHotel;

        player.pay(cost);
    }

}
