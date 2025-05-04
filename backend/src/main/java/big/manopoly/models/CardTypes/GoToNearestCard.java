package big.manopoly.models.CardTypes;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

import big.manopoly.models.Board;
import big.manopoly.models.BoardSquare;
import big.manopoly.models.Card;
import big.manopoly.models.Player;
import big.manopoly.models.Property;
import big.manopoly.services.BoardService;
import big.manopoly.utils.PropertyType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("GoToNearestCard")
// a card used for when a player has to go to the nearest kind of a property.
public class GoToNearestCard extends Card {
    @Enumerated(EnumType.STRING)
    private PropertyType targetType;

    public GoToNearestCard() {
        super();
    }

    @JsonCreator
    public GoToNearestCard(@JsonProperty("message") String message,
            @JsonProperty("targetType") PropertyType targetType) {
        super(message);
        this.message = message;
        this.targetType = targetType;
    }

    @Override
    public void action(Player player) {
        int currentPlayerPosition = player.getPosition().getPosition();
        int index = currentPlayerPosition;
        Board board = player.getBoard();

        for (int i = 0; i < 40; i++) {
            index++;

            if (index >= 40) {
                index = 0;
            }

            BoardSquare square = (BoardSquare) Hibernate.unproxy(board.getBoardSquare(index));

            if (!(square instanceof Property)) {
                continue;
            }

            Property property = (Property) square;

            if (property.getType().equals(targetType)) {
                break;
            }
        }

        GoToCard.goToTargetSquare(player, currentPlayerPosition, index);
    }

}
