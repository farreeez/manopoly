package big.manopoly.models.CardTypes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.models.Board;
import big.manopoly.models.Card;
import big.manopoly.models.Player;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GetMoneyCard")
// a card used for chance and chest card where the player has to get money from
// the bank or the players fromOtherPlayers specifies which one it is.
public class GetMoneyCard extends Card {
    private int moneyOwed;
    private Boolean fromOtherPlayers;

    public GetMoneyCard() {
        super();
    }

    @JsonCreator
    public GetMoneyCard(@JsonProperty("message") String message,
            @JsonProperty("moneyOwed") int moneyOwed, @JsonProperty("fromOtherPlayers") Boolean fromOtherPlayers) {
        super(message);
        this.message = message;
        this.moneyOwed = moneyOwed;
        this.fromOtherPlayers = fromOtherPlayers;
    }

    @Override
    public void action(Player player, BoardRepository boardRepository, PlayerRepository playerRepository) {
        if (!fromOtherPlayers) {
            player.addMoney(moneyOwed);
            playerRepository.save(player);
            return;
        }

        Board board = player.getBoard();

        List<Player> players = board.getPlayers();

        int totalMoney = 0;

        for (int i = 0; i < players.size(); i++) {
            if (player.equals(players.get(i))) {
                continue;
            }

            players.get(i).pay(moneyOwed);

            totalMoney += moneyOwed;
        }

        player.addMoney(totalMoney);

        boardRepository.save(board);
    }
}
