package big.manopoly.utils;

import big.manopoly.dtos.CardPurchaseActionDTO;
import big.manopoly.dtos.TileActionDTO;
import big.manopoly.models.Board;
import big.manopoly.models.BoardSquare;
import big.manopoly.models.NotProperty;
import big.manopoly.models.Player;
import big.manopoly.models.Property;

public class TileActions {
    // checks if the tile landed on is a property or not and the allocates it
    // accordingly
    public static TileActionDTO conductTileAction(Player player, Board board, int[] diceRolls) throws Exception {
        BoardSquare square = board.getSquares().get(player.getPosition().getPosition());
        if(square instanceof Property) {
            Property property = (Property) square;
            return conductPropertyAction(player, board, property, diceRolls);
        } else if(square instanceof NotProperty){
            NotProperty notProperty = (NotProperty) square;
            return conductNonPropertyAction(player, board, notProperty, diceRolls);
        } else {
            throw new Error("BoardSquare of invalid class type in conduct tile action in big.manopoly.utils.TileActions.java");
        }

    }

    public static TileActionDTO conductPropertyAction(Player player, Board board, Property property, int[] diceRolls) throws Exception {
        Player owner = property.getOwner();

        if(owner == null) {
            // card owned by the bank ask player if they want to purchase it
            return new CardPurchaseActionDTO(property.getPrice());
        } else if (owner == player) {
            // player owns card do nothing and return empty actionDTO
            return new TileActionDTO();
        } else {
            // other player owns property pay rent.

            // case player has rent money.
            // TODO: finish negative balance functionality.
            // TODO: display all transactions in real time to all players
            int rent = property.getRent();
            player.setMoney(player.getMoney() - rent);

            // if player cant afford the rent and has a negative balance only transfer the amoun that they can afford to the owner
            if(player.getMoney() < 0) {
                rent += player.getMoney();
            }

            if(rent < 0) {
                throw new Exception("rent money should not be negative in conductPropertyAction in big.manopoly.utils.TileActions");
            }

            owner.setMoney(owner.getMoney() + rent);

            return new TileActionDTO();
        }
    }

    // TODO: finish this once chance card and jail functionality is complete.
    public static TileActionDTO conductNonPropertyAction(Player player, Board board, NotProperty notProperty, int[] diceRolls) {
        return new TileActionDTO();
    }
}
