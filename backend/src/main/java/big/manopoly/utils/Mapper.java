package big.manopoly.utils;

import big.manopoly.dtos.BoardDTO;
import big.manopoly.dtos.BoardSquareDTO;
import big.manopoly.dtos.PlayerDTO;
import big.manopoly.dtos.PropertyDTO;
import big.manopoly.models.Board;
import big.manopoly.models.BoardSquare;
import big.manopoly.models.City;
import big.manopoly.models.Colour;
import big.manopoly.models.Player;
import big.manopoly.models.Property;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

public class Mapper {
    public static BoardDTO toBoardDTO(Board board, boolean rollDiceAction, int lastBoughtPosition) {
        return new BoardDTO(
                board.getId(),
                board.getPlayers().stream().map(Player::getId).collect(Collectors.toList()),
                board.getSquares().stream().map(BoardSquare::getId).collect(Collectors.toList()),
                board.getTakenColours().stream().map(Mapper::playerColourToColour).collect(Collectors.toList()),
                board.getPossibleColours().stream().map(Mapper::playerColourToColour).collect(Collectors.toList()),
                toPlayerDTO(board.getPlayerWithCurrentTurn()),
                board.isDiceRolled(),
                board.getDiceRolls(),
                rollDiceAction,
                lastBoughtPosition);
    }

    public static Colour playerColourToColour(PlayerColour playerColour) {
        if (playerColour == null) {
            return null;
        }

        return new Colour(playerColour.getRed(), playerColour.getGreen(), playerColour.getBlue(),
                playerColour.ordinal());
    }

    public static BoardSquareDTO toBoardSquareDTO(BoardSquare proxyBoardSquare) {
        Long boardId = proxyBoardSquare.getBoard() != null ? proxyBoardSquare.getBoard().getId() : null;

        BoardSquare boardSquare = (BoardSquare) Hibernate.unproxy(proxyBoardSquare);

        boolean isProperty = boardSquare instanceof Property;

        return new BoardSquareDTO(boardSquare.getId(), boardSquare.getPosition(), boardSquare.getName().replace("_", " "),
                boardId, boardSquare.getPrice(), isProperty);
    }

    public static PropertyDTO toPropertyDTO(Property property) {
        if (property instanceof HibernateProxy) {
            property = (Property) ((HibernateProxy) property).getHibernateLazyInitializer().getImplementation();
        }

        Long boardId = property.getBoard() != null ? property.getBoard().getId() : null;
        Long ownerId = property.getOwner() != null ? property.getOwner().getId() : null;
        String ownerName = property.getOwner() != null ? property.getOwner().getName() : null;
        Colour playerColour = property.getOwner() != null ? playerColourToColour(property.getOwner().getColour())
                : null;
        int houseCost = property instanceof City ? ((City) property).getHouseCost() : 0;

        return new PropertyDTO(property.getId(), property.getPosition(), property.getName().replace("_", " "), boardId,
                property.getPrice(), property.getType(), property.isMortgaged(), ownerId, ownerName,
                playerColour, property.getPossibleRents(), property.getMortgagePayout(), property.getMortgageCost(),
                houseCost);
    }

    public static PlayerDTO toPlayerDTO(Player player) {
        if (player == null) {
            return null;
        }

        return new PlayerDTO(player.getId(), player.getName(), playerColourToColour(player.getColour()),
                player.getPosition(),
                player.getBoardId(), player.getMoney(),
                player.getProperties().stream().map(Mapper::toPropertyDTO).collect(Collectors.toSet()),
                player.isFree());
    }
}
