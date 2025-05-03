package big.manopoly.models.CardTypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.models.Card;
import big.manopoly.models.Player;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GetOutOfJailCard")
public class GetOutOfJailCard extends Card {

    public GetOutOfJailCard() {
        super();
    }

    @JsonCreator
    public GetOutOfJailCard(@JsonProperty("message") String message) {
        super(message);
        this.message = message;
    }

    @Override
    public void action(Player player, BoardRepository boardRepository, PlayerRepository playerRepository) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }

}
