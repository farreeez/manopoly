package big.manopoly.utils;

import java.util.stream.Collectors;

import big.manopoly.dtos.BoardDTO;
import big.manopoly.dtos.BoardSquareDTO;
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

    public static BoardSquareDTO toBoardSquareDTO(BoardSquare boardSquare) {
        Long boardId = boardSquare.getBoard() != null ? boardSquare.getBoard().getId() : null;

        return new BoardSquareDTO(boardSquare.getId(), boardSquare.getPosition(), boardSquare.getName(),
                boardId, boardSquare.getPrice());
    }
}
