package big.manopoly.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.BoardSquareRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.dtos.BoardDTO;
import big.manopoly.dtos.BoardSquareDTO;
import big.manopoly.dtos.TileActionDTO;
import big.manopoly.models.Board;
import big.manopoly.models.BoardSquare;
import big.manopoly.models.Player;
import big.manopoly.utils.BoardServicesUtility;
import big.manopoly.utils.Mapper;
import big.manopoly.utils.TileActions;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/board")
public class BoardResource {

    private final BoardRepository boardRepository;
    private final PlayerRepository playerRepository;
    private final BoardSquareRepository boardSqRepository;

    @Autowired
    public BoardResource(BoardRepository boardRepository, PlayerRepository playerRepository,
            BoardSquareRepository boardSquareRepository) {
        this.boardRepository = boardRepository;
        this.playerRepository = playerRepository;
        this.boardSqRepository = boardSquareRepository;
    }

    @PostMapping("/createBoard")
    public ResponseEntity<?> createBoard(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Board board = new Board();

        return BoardServicesUtility.addPlayerFromCookie(cookie, board, playerRepository, boardRepository);
    }

    @GetMapping("/getBoard/{id}")
    public ResponseEntity<?> getBoard(@PathVariable Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (!optionalBoard.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Board board = optionalBoard.get();

        BoardDTO boardDTO = Mapper.toBoardDTO(board, false);

        return ResponseEntity.ok().body(boardDTO);
    }

    @PostMapping("/joinBoard/{id}")
    public ResponseEntity<?> joinBoard(@CookieValue(value = "playerId", defaultValue = "") String cookie,
            @PathVariable Long id) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Board board;
        try {
            board = boardRepository.getReferenceById(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return BoardServicesUtility.addPlayerFromCookie(cookie, board, playerRepository, boardRepository);
    }

    @DeleteMapping("/leaveBoard")
    public ResponseEntity<?> leaveBoard(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
        if (cookie.isEmpty()) {
            System.out.println("here1");
            return ResponseEntity.badRequest().build();
        }

        Player player;

        try {
            Long playerId = Long.valueOf(cookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
            System.out.println("here2");
            return ResponseEntity.badRequest().body("Invalid playerId cookie value");
        }

        BoardServicesUtility.removePlayer(player, boardRepository, playerRepository);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getBoardSquare/{id}")
    public ResponseEntity<?> getBoardSquare(@PathVariable String id) {
        BoardSquare square;

        try {
            square = boardSqRepository.getReferenceById(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        BoardSquareDTO squareDTO = Mapper.toBoardSquareDTO(square);

        return ResponseEntity.ok().body(squareDTO);
    }

    @GetMapping("/subscribeToBoard/{id}")
    public void subscribeToBoard(
            @PathVariable Long id, HttpServletRequest request) {

        System.out.println("subscribed");
        AsyncContext sub = request.startAsync();
        sub.setTimeout(1800000);
        BoardSubscriptionManager.instance().addSubscription(id, sub);
    }

    @GetMapping("/processSubs/{id}")
    public ResponseEntity<?> processSubs(@PathVariable Long id) {
        BoardSubscriptionManager.instance().processSubsFor(id, boardRepository, false);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/chooseColour/{colourId}")
    public ResponseEntity<?> chooseColour(@CookieValue(value = "playerId", defaultValue = "") String cookie,
            @PathVariable int colourId) {
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

        return player.setColour(colourId, boardRepository, playerRepository);
    }

    @PostMapping("/rollDice")
    public ResponseEntity<?> rollDice(@CookieValue(value = "playerId", defaultValue = "") String cookie) throws Exception {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().body("player cookie is empty.");
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
            return ResponseEntity.badRequest().body("player cannot roll the dice as they have no board.");
        }

        if (player.getId() != board.getPlayerWithCurrentTurn().getId()) {
            return ResponseEntity.badRequest().body("player cannot roll the dice as it is not their turn.");
        }

        if (board.isDiceRolled()) {
            return ResponseEntity.badRequest().body("player cannot roll the dice it has already been rolled.");
        }

        int[] diceRolls = board.movePlayer();

        TileActionDTO action = TileActions.conductTileAction(player, board, diceRolls);

        board.saveBoard(boardRepository, true);

        return ResponseEntity.ok().body(action);
    }

    @PostMapping("/endTurn")
    public ResponseEntity<?> endTurn(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().body("player cookie is empty.");
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
            return ResponseEntity.badRequest().body("player cannot end the turn as they have no board.");
        }

        if (player.getId() != board.getPlayerWithCurrentTurn().getId()) {
            return ResponseEntity.badRequest().body("player cannot end the turn as it is not their turn.");
        }

        if (!board.endTurn()) {
            return ResponseEntity.badRequest().body("player cannot end the turn as the dice has not been rolled.");
        }

        board.saveBoard(boardRepository, false);

        return ResponseEntity.ok().build();
    }
}
