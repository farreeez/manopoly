package big.manopoly.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Card {
    @Id
    protected String id;

    protected Boolean ofTypeChance;

    protected String message;

    public Card() {
    }

    @JsonCreator
    public Card(@JsonProperty("ofTypeChance") Boolean ofTypeChance, @JsonProperty("message") String message) {
        String cardType = ofTypeChance ? "Chance" : "Chest";

        id = cardType + ":" + message;
    }

    public abstract void action(Player player);

    public String getId() {
        return id;
    }

    public Boolean isOfTypeChance() {
        return ofTypeChance;
    }

    public String getCardMessage() {
        return message;
    }
}
