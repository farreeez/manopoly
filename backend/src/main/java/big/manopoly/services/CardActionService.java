package big.manopoly.services;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.data.PropertyRepository;
import big.manopoly.dtos.PropertyDTO;
import big.manopoly.dtos.TileActionDTO;
import big.manopoly.models.Board;
import big.manopoly.models.Player;
import big.manopoly.models.Property;
import big.manopoly.utils.Mapper;
import big.manopoly.utils.PropertyUtils;
import big.manopoly.utils.TileActions;

@Service
public class CardActionService {

    private final BoardRepository boardRepository;
    private final PlayerRepository playerRepository;
    private final PropertyRepository propertyRepository;

    @Autowired
    public CardActionService(BoardRepository boardRepository, PlayerRepository playerRepository,
            PropertyRepository propertyRepository) {
        this.boardRepository = boardRepository;
        this.playerRepository = playerRepository;
        this.propertyRepository = propertyRepository;
    }

    public ResponseEntity<?> buyProperty(String playerIdCookie) {
        Player player;
        try {
            Long playerId = Long.valueOf(playerIdCookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid playerId cookie value");
        }

        Board board = player.getBoard();
        if (board == null) {
            return ResponseEntity.badRequest()
                    .body("player cannot buy the property as they are not currently in a game.");
        }

        if (player.getId() != board.getPlayerWithCurrentTurn().getId()) {
            return ResponseEntity.badRequest()
                    .body("player cannot buy the property as it is not currently their turn.");
        }

        try {
            PropertyUtils.buyPropertyFromBoard(player, playerRepository, boardRepository);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        TileActionDTO actionDTO;
        try {
            actionDTO = TileActions.conductTileAction(player, board, board.getDiceRolls());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().body(actionDTO);
    }

    public ResponseEntity<?> getProperty(String id) {
        Property property;
        try {
            property = propertyRepository.getReferenceById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        PropertyDTO propertyDTO = Mapper.toPropertyDTO(property);
        return ResponseEntity.ok().body(propertyDTO);
    }

    public ResponseEntity<?> mortgageProperty(String propertyId, String playerIdCookie) {
        Player player;

        try {
            Long playerId = Long.valueOf(playerIdCookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid playerId cookie value");
        }

        Board board = player.getBoard();

        if (board == null) {
            return ResponseEntity.badRequest()
                    .body("player cannot buy the property as they are not currently in a game.");
        }

        Property property;
        try {
            property = propertyRepository.getReferenceById(propertyId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        if (property.getOwner() == null || (property.getOwner() != player)) {
            return ResponseEntity.badRequest().body("property is not owned by the player.");
        }
        
        if (player.isHouseBuiltOnSet(property.getType())) {
            return ResponseEntity.badRequest().body("cannot mortgage property as houses are built on the set.");
        } 

        if (board.getPlayerWithCurrentTurn() != player) {
            return ResponseEntity.badRequest().body("cannot mortgage the property as it is not the player's current turn.");
        }

        if (property.isMortgaged()) {
            return ResponseEntity.badRequest().body("cannot mortgage property as it is already mortgaged.");
        }

        property.setMortgaged(true);

        player.addMoney(property.getMortgagePayout());

        playerRepository.save(player);
        propertyRepository.save(property);

        BoardSubscriptionManager.instance().processSubsFor(board.getId(), boardRepository,false, -1);

        return ResponseEntity.ok().build();
    }
}