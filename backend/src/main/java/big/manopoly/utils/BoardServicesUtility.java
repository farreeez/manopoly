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

public class BoardServicesUtility {
    public static ResponseEntity<?> addPlayerFromCookie(String cookie, Board board, PlayerRepository playerRepository,
            BoardRepository boardRepository) {
        Player player;

        try {
            Long playerId = Long.valueOf(cookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid playerId cookie value");
        }

        // creating a new board should remove player from previous board
        if (player.getBoard() != null) {
            removePlayer(player, boardRepository, playerRepository);
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

        BoardSubscriptionManager.instance().processSubsFor(board.getId(), boardRepository);

        return ResponseEntity.created(location)
                .body(BoardDTO);
    }

    public static void removePlayer(Player player, BoardRepository boardRepository, PlayerRepository playerRepository) {
        Board playerBoard = player.getBoard();

        if (playerBoard == null) {
            return;
        }

        playerBoard.removePlayer(player);

        PlayerColour colour = player.getColour();
        player.setColour(null);

        playerBoard.giveBackColour(colour);

        boardRepository.save(playerBoard);
        playerRepository.save(player);

        BoardSubscriptionManager.instance().processSubsFor(playerBoard.getId(), boardRepository);
    }
}
