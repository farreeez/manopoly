package big.manopoly.utils;

import java.util.stream.Collectors;

import big.manopoly.dtos.BoardDTO;
import big.manopoly.dtos.BoardSquareDTO;
import big.manopoly.dtos.PlayerDTO;
import big.manopoly.dtos.PropertyDTO;
import big.manopoly.models.Board;
import big.manopoly.models.BoardSquare;
import big.manopoly.models.Colour;
import big.manopoly.models.Player;
import big.manopoly.models.Property;

public class Mapper {
    public static BoardDTO toBoardDTO(Board board) {
        return new BoardDTO(
                board.getId(),
                board.getPlayers().stream().map(Player::getId).collect(Collectors.toList()),
                board.getSquares().stream().map(BoardSquare::getId).collect(Collectors.toList()),
                board.getTakenColours().stream().map(Mapper::playerColourToColour).collect(Collectors.toList()),
                board.getPossibleColours().stream().map(Mapper::playerColourToColour).collect(Collectors.toList()),
                toPlayerDTO(board.getPlayerWithCurrentTurn()),
                board.isDiceRolled(),
                board.getDiceRolls());
    }

    public static Colour playerColourToColour(PlayerColour playerColour) {
        if(playerColour == null) {
            return null;
        }

        return new Colour(playerColour.getRed(), playerColour.getGreen(), playerColour.getBlue(),
                playerColour.ordinal());
    }

    public static BoardSquareDTO toBoardSquareDTO(BoardSquare boardSquare) {
        Long boardId = boardSquare.getBoard() != null ? boardSquare.getBoard().getId() : null;

        return new BoardSquareDTO(boardSquare.getId(), boardSquare.getPosition(), boardSquare.getName(),
                boardId, boardSquare.getPrice());
    }

    public static PropertyDTO toPropertyDTO(Property property) {
        Long boardId = property.getBoard() != null ? property.getBoard().getId() : null;
        Long ownerId = property.getOwner() != null ? property.getOwner().getId() : null;
        return new PropertyDTO(property.getId(), property.getPosition(), property.getName(), boardId,
                property.getPrice(), property.getType(), property.isMortgaged(), ownerId, property.getName());
    }

    public static PlayerDTO toPlayerDTO(Player player) {
        if(player == null) {
            return null;
        }
        
        return new PlayerDTO(player.getId(), player.getName(), playerColourToColour(player.getColour()),
                player.getPosition(),
                player.getBoardId(), player.getMoney(),
                player.getProperties().stream().map(Mapper::toPropertyDTO).collect(Collectors.toSet()),
                player.isFree());
    }
}
