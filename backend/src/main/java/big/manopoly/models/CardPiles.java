package big.manopoly.models;

import java.util.LinkedList;
import java.util.Queue;

import jakarta.persistence.Embeddable;

//TODO finish card functionality
@Embeddable
public class CardPiles {
    private Queue<Integer> cards;

    // determines if community chest pile or chance pile (true if chance pile).
    private boolean chancePile;

    public CardPiles(int position) {
        // super(position);

        //TODO add list of cards and make card type.
        cards = new LinkedList<>();
    }

    // gets a card at the top of the pile then puts it at the bottom
    public Integer getCard() {
        int num = cards.poll();
        cards.add(num);

        return num;
    }

    public void setChancePile(boolean chancePile) {
        this.chancePile = chancePile;
    }

    public boolean isChancePile() {
        return chancePile;
    }
}
