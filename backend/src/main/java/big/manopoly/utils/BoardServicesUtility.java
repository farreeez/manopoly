package big.manopoly.utils;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.models.Board;
import big.manopoly.models.Player;

public class BoardServicesUtility {
    public static boolean addPlayerToBoard(Player player, Board board, PlayerRepository playerRepository, BoardRepository boardRepository) {
        // creating a new board should remove player from previous board
        if (player.getBoard() != null) {
            Board playerBoard = player.getBoard();
            playerBoard.removePlayer(player);
            boardRepository.save(playerBoard);
        }

        boolean checkAdded = board.addPlayer(player);

        if (!checkAdded) {
            return false;
        }

        boardRepository.save(board);
        playerRepository.save(player);

        return true;
    }
}
