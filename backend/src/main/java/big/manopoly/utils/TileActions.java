package big.manopoly.utils;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.dtos.CardPurchaseActionDTO;
import big.manopoly.dtos.TileActionDTO;
import big.manopoly.models.Board;
import big.manopoly.models.BoardSquare;
import big.manopoly.models.Card;
import big.manopoly.models.CardPile;
import big.manopoly.models.ChanceSquare;
import big.manopoly.models.ChestSquare;
import big.manopoly.models.IncomeTax;
import big.manopoly.models.Jail;
import big.manopoly.models.NotProperty;
import big.manopoly.models.Player;
import big.manopoly.models.Property;

public class TileActions {
    // checks if the tile landed on is a property or not and the allocates it
    // accordingly
    public static TileActionDTO conductTileAction(Player player, Board board, int[] diceRolls,
            BoardRepository boardRepository, PlayerRepository playerRepository) throws Exception {
        BoardSquare square = board.getSquares().get(player.getPosition().getPosition());
        if (square instanceof Property) {
            Property property = (Property) square;
            return conductPropertyAction(player, board, property, diceRolls);
        } else if (square instanceof IncomeTax) {
            IncomeTax incomeTax = (IncomeTax) square;
            return conductIncomeTaxAction(player, board, incomeTax, diceRolls);
        } else if (square instanceof Jail) {
            return conductJailAction(player, board, diceRolls);
        } else if (square instanceof ChanceSquare) {
            CardPile cardPile = board.getCardPile();
            Card card = cardPile.getChanceCard();
            return conductCardAction(player, board, card, diceRolls, boardRepository, playerRepository);
        } else if (square instanceof ChestSquare) {
            CardPile cardPile = board.getCardPile();
            Card card = cardPile.getChestCard();
            return conductCardAction(player, board, card, diceRolls, boardRepository, playerRepository);
        } else if (square instanceof NotProperty) {
            NotProperty notProperty = (NotProperty) square;
            return conductNonPropertyAction(player, board, notProperty, diceRolls);
        } else {
            throw new Error(
                    "BoardSquare of invalid class type in conduct tile action in big.manopoly.utils.TileActions.java");
        }

    }

    private static TileActionDTO conductCardAction(Player player, Board board, Card card, int[] diceRolls,
            BoardRepository boardRepository, PlayerRepository playerRepository) throws Exception {
        card.action(player);

        // do something with message
        System.out.println(card.getCardMessage());

        return conductTileAction(player, board, diceRolls, boardRepository, playerRepository);
    }

    public static TileActionDTO conductPropertyAction(Player player, Board board, Property property, int[] diceRolls)
            throws Exception {
        Player owner = property.getOwner();

        if (owner == null) {
            // card owned by the bank ask player if they want to purchase it
            return new CardPurchaseActionDTO(diceRolls, property.getPrice());
        }

        if (owner != player && !property.isMortgaged() && property.getOwner().isFree()) {
            // TODO: finish negative balance functionality.
            // TODO: display all transactions in real time to all players
            int rent = property.getRent();

            if (rent < 0) {
                throw new Exception(
                        "rent money should not be negative in conductPropertyAction in big.manopoly.utils.TileActions");
            }

            player.setMoney(player.getMoney() - rent);

            // if player cant afford the rent and has a negative balance only transfer the
            // amount that they can afford to the owner
            if (player.getMoney() < 0) {
                // this is so that the owner does not receive money that doesn't exist yet.
                // (player.getMoney() is negative at this stage.)
                rent += player.getMoney();
            }

            owner.setMoney(owner.getMoney() + rent);
        }

        return new TileActionDTO(diceRolls);
    }

    // TODO: finish this once chance card and jail functionality is complete.
    public static TileActionDTO conductNonPropertyAction(Player player, Board board, NotProperty notProperty,
            int[] diceRolls) {
        return new TileActionDTO(diceRolls);
    }

    public static TileActionDTO conductIncomeTaxAction(Player player, Board board, IncomeTax incomeTax,
            int[] diceRolls) {
        player.pay(incomeTax.getTax(player.getMoney()));

        return new TileActionDTO(diceRolls);
    }

    public static TileActionDTO conductJailAction(Player player, Board board, int[] diceRolls) throws Exception {
        player.setFree(false);
        player.resetJailCounter();
        player.getPosition().add(20);

        if (!board.endTurn()) {
            throw new Exception("player could not end turn after being jailed.");
        }

        return new TileActionDTO(diceRolls);
    }
}
