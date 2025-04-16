package big.manopoly.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/cardActions")
public class CardActionResource {

    private final BoardRepository boardRepository;
    private final PlayerRepository playerRepository;
    private final PropertyRepository propertyRepository;

    public CardActionResource(BoardRepository boardRepository, PlayerRepository playerRepository, PropertyRepository propertyRepository) {
        this.boardRepository = boardRepository;
        this.playerRepository = playerRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/buyProperty")
    public ResponseEntity<?> buyProperty(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Player player;

        try {
            Long playerId = Long.valueOf(cookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
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
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        TileActionDTO actionDTO;

        try {
            actionDTO = TileActions.conductTileAction(player, board, board.getDiceRolls());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().body(actionDTO);
    }

    @GetMapping("/getProperty/{id}")
    public ResponseEntity<?> getProperty(@PathVariable String id) {
        Property property;

        try {
            property = propertyRepository.getReferenceById(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        PropertyDTO propertyDTO = Mapper.toPropertyDTO(property);

        return ResponseEntity.ok().body(propertyDTO);
    }

}
