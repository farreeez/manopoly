package big.manopoly.models;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Card", discriminatorType = DiscriminatorType.STRING)
public abstract class Card {
    @Id
    @GeneratedValue
    protected Long id;

    protected String message;

    public Card() {
    }

    public Card(String message) {
        this.message = message;
    }

    public abstract void action(Player player, BoardRepository boardRepository, PlayerRepository playerRepository);

    public String getCardMessage() {
        return message;
    }

    public void setCardMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }
}
