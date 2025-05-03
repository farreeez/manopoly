package big.manopoly.models;

import java.util.ArrayList;

import big.manopoly.models.CardTypes.*;
import jakarta.persistence.Embeddable;

@Embeddable
public class ChestPile extends CardPile {
    public ChestPile() {
        this.cards = new ArrayList<Card>();

        cards.add(new GoToJailCard("Go to jail"));
        cards.add(new GetOutOfJailCard("Get out of jail, free"));
        cards.add(new GetMoneyCard("Life insurance matures - Collect $100", 100, false));
        cards.add(new GetMoneyCard("Income tax refund - Collect $20", 20, false));
        cards.add(new GetMoneyCard("You have won second prize in a beauty contest - Collect $10", 10, false));
        cards.add(new GoToCard("Advance to GO (Collect $200)", 0));
        cards.add(new PayMoneyCard("Pay hospital $100", 100, false));
        cards.add(new GetMoneyCard("You inherit $100", 100, false));
        cards.add(new GetMoneyCard("Grand opera opening - Collect $50 from every player", 50, true));
        cards.add(new GetMoneyCard("Bank error in your favor - Collect $200", 200, false));
        cards.add(new PayMoneyCard("Doctor's fee - Pay $50", 50, false));
        cards.add(new GetMoneyCard("Receive for services $25", 25, false));
        cards.add(new PayMoneyCard("Pay school tax of $150", 150, false));
        cards.add(new PayMoneyPerHouseCard("You are assessed for street repairs - $40 per house - $115 per hotel", 0, 40, 115));
        cards.add(new GetMoneyCard("Christmas fund matures - Collect $100", 100, false));
    }
}
