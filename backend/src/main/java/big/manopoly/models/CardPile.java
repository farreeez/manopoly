package big.manopoly.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;

@MappedSuperclass
public abstract class CardPile {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<Card> cards;

    @Column(name = "current_card_index")
    protected int currentCardIndex;

    // gets a card at the top of the pile then puts it at the bottom
    public Card getCard() {
        if(currentCardIndex >= cards.size()) {
            currentCardIndex = 0;
        }

        Card card = cards.get(currentCardIndex);

        currentCardIndex++;

        return card;
    }
}
