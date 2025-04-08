package big.manopoly.utils;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.models.BoardSquare;
import big.manopoly.models.Player;
import big.manopoly.models.Property;
import big.manopoly.services.BoardSubscriptionManager;

public class PropertyUtils {
    // this is private because I want to use it both for when you buy a property after landing on it and for trading with players.
    private static void buyProperty(Player player, Property property, PlayerRepository playerRepository, BoardRepository boardRepository) throws Exception {

        int price = property.getPrice();

        if(player.getMoney() < price) {
            throw new Exception("Player cannot afford the property");
        }

        player.addProperty(property);

        player.pay(price);

        playerRepository.save(player); 

        BoardSubscriptionManager.instance().processSubsFor(player.getBoardId(), boardRepository, false);
    }

    public static void buyPropertyFromBoard(Player player, PlayerRepository playerRepository, BoardRepository boardRepository) throws Exception {
        BoardSquare square = player.getCurrentBoardSquare();

        if(!(square instanceof Property)) {
            throw new Exception("current player square is not a property");
        }
        
        Property property = (Property) square;

        if(property.getOwner() != null) {
            throw new Exception("property is already owned by another player");
        }

        buyProperty(player, property, playerRepository, boardRepository);
    }
}
