package big.manopoly.utils;

import java.util.stream.Collectors;

import big.manopoly.dtos.BoardDTO;
import big.manopoly.models.Board;
import big.manopoly.models.BoardSquare;
import big.manopoly.models.Player;

public class Mapper {
    public static BoardDTO toBoardDTO(Board board) {
        return new BoardDTO(
                board.getId(),
                board.getPlayers().stream().map(Player::getId).collect(Collectors.toSet()),
                board.getSquares().stream().map(BoardSquare::getId).collect(Collectors.toList()));
    }
}
