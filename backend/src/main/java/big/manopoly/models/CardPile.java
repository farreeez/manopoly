package big.manopoly.models;

import java.util.ArrayList;
import java.util.List;

import big.manopoly.models.CardTypes.GetMoneyCard;
import big.manopoly.models.CardTypes.GetOutOfJailCard;
import big.manopoly.models.CardTypes.GoToCard;
import big.manopoly.models.CardTypes.GoToJailCard;
import big.manopoly.models.CardTypes.GoToNearestCard;
import big.manopoly.models.CardTypes.PayMoneyCard;
import big.manopoly.models.CardTypes.PayMoneyPerHouseCard;
import big.manopoly.utils.PropertyType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Embeddable
public class CardPile {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<Card> cards;

    protected int chestIndex;

    protected int chanceIndex;

    public CardPile() {
        chestIndex = 0;
        chanceIndex = 0;

        cards = new ArrayList<>();

        // community chest cards
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
        cards.add(new PayMoneyPerHouseCard("You are assessed for street repairs - $40 per house - $115 per hotel", 40,
                115));
        cards.add(new GetMoneyCard("Christmas fund matures - Collect $100", 100, false));

        // chance cards
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


    public Card getChestCard() {
        if (chestIndex > 14) {
            chestIndex = 0;
        }

        Card card = cards.get(chestIndex);

        chestIndex++;

        return card;
    }

    public Card getChanceCard() {
        if (chanceIndex >= cards.size()) {
            chanceIndex = 15;
        }

        chanceIndex = 16;

        Card card = cards.get(chanceIndex);

        chanceIndex++;

        return card;
    }
}
