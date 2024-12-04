package big.manopoly.utils;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.dtos.BoardDTO;
import big.manopoly.models.Board;
import big.manopoly.models.Player;
import big.manopoly.services.BoardSubscriptionManager;
import jakarta.servlet.AsyncContext;

public class BoardServicesUtility {
    public static ResponseEntity<?> addPlayerFromCookie(String cookie, Board board, PlayerRepository playerRepository,
            BoardRepository boardRepository, AsyncContext sub) {
        Player player;

        try {
            Long playerId = Long.valueOf(cookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid playerId cookie value");
        }

        // creating a new board should remove player from previous board
        if (player.getBoard() != null) {
            removePlayer(player, boardRepository);
        }

        boolean checkAdded = board.addPlayer(player);

        if (!checkAdded) {
            return ResponseEntity.badRequest().build();
        }

        boardRepository.save(board);
        playerRepository.save(player);

        BoardDTO BoardDTO = Mapper.toBoardDTO(board);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/getBoard/{id}")
                .buildAndExpand(board.getId()).toUri();

        BoardSubscriptionManager.instance().addSubscription(board.getId(), sub);

        return ResponseEntity.created(location)
                .body(BoardDTO);
    }

    public static void removePlayer(Player player, BoardRepository boardRepository) {
        Board playerBoard = player.getBoard();

        if (playerBoard == null) {
            return;
        }

        playerBoard.removePlayer(player);
        boardRepository.save(playerBoard);

        BoardSubscriptionManager.instance().processSubsFor(playerBoard.getId(), boardRepository);
    }
}
