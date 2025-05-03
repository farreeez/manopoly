package big.manopoly.models;

import java.util.ArrayList;
import big.manopoly.models.CardTypes.*;
import big.manopoly.utils.PropertyType;
import jakarta.persistence.Embeddable;

@Embeddable
public class ChancePile extends CardPile {
    public ChancePile() {
        this.cards = new ArrayList<Card>();

        cards.add(new GoToCard("Take a ride on the Grand Central Terminal.", 5));
        cards.add(new GoToNearestCard("Advance to nearest utility. If unowned you may buy it from bank",
                PropertyType.UTILITY));
        cards.add(new GoToCard("Go back 3 spaces", -3));
        cards.add(new GoToCard("Advance to GO (Collect $200)", 0));
        cards.add(new GetMoneyCard("Bank pays you dividend of $50", 50, false));
        cards.add(new GoToCard("Advance to Shanghai.", 24));
        cards.add(new PayMoneyPerHouseCard(
                "Make General Repairs on all your property. Pay $25 for each house, $100 for each hotel", 25,
                100));
        cards.add(new GetOutOfJailCard("Get out of jail free"));
        cards.add(new GoToCard("Advance to Malmö", 39));
        cards.add(new PayMoneyCard("Pay poor tax of $15", 15, false));
        cards.add(new GoToNearestCard(
                "Advance to the nearest Railroad. If Railroad is unowned, you may buy it from the bank",
                PropertyType.TRAIN));
        cards.add(new GoToJailCard("Go directly to Jail. Do not pass GO, do not collect $200"));
        cards.add(new PayMoneyCard("You have been elected Chairman of the Board. Pay each player $50", 50, true));
        cards.add(new GoToCard("Advance to Kyoto. If you pass GO, collect $200", 11));
        cards.add(new GetMoneyCard("Your building and loan matures. Collect $150", 150, false));
    }
}
