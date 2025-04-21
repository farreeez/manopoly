package big.manopoly.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final PlayerRepository playerRepository;
    private final BoardSquareRepository boardSqRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, PlayerRepository playerRepository,
            BoardSquareRepository boardSquareRepository) {
        this.boardRepository = boardRepository;
        this.playerRepository = playerRepository;
        this.boardSqRepository = boardSquareRepository;
    }

    public ResponseEntity<?> createBoard(String cookie) {
        Board board = new Board();

        return BoardServicesUtility.addPlayerFromCookie(cookie, board, playerRepository, boardRepository);
    }

    public ResponseEntity<?> getBoard(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (!optionalBoard.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Board board = optionalBoard.get();

        BoardDTO boardDTO = Mapper.toBoardDTO(board, false, -1);

        return ResponseEntity.ok().body(boardDTO);
    }

    public ResponseEntity<?> joinBoard(String cookie, Long id) {
        Board board;
        try {
            board = boardRepository.getReferenceById(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return BoardServicesUtility.addPlayerFromCookie(cookie, board, playerRepository, boardRepository);
    }

    public ResponseEntity<?> leaveBoard(String cookie) {
        Player player;

        try {
            Long playerId = Long.valueOf(cookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid playerId cookie value");
        }

        BoardServicesUtility.removePlayer(player, boardRepository, playerRepository);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getBoardSquare(String id) {
        BoardSquare square;

        try {
            square = boardSqRepository.getReferenceById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        BoardSquareDTO squareDTO = Mapper.toBoardSquareDTO(square);

        return ResponseEntity.ok().body(squareDTO);
    }

    public void subscribeToBoard(Long id, HttpServletRequest request) {
        AsyncContext sub = request.startAsync();
        sub.setTimeout(1800000);
        BoardSubscriptionManager.instance().addSubscription(id, sub);
    }

    public ResponseEntity<?> processSubs(Long id) {
        BoardSubscriptionManager.instance().processSubsFor(id, boardRepository, false, -1);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> chooseColour(String cookie, int colourId) {
        Player player;

        try {
            Long playerId = Long.valueOf(cookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid playerId cookie value");
        }

        return player.setColour(colourId, boardRepository, playerRepository);
    }

    public ResponseEntity<?> rollDice(String playerIdCookie) throws Exception {
        Player player;

        try {
            Long playerId = Long.valueOf(playerIdCookie);
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

    public ResponseEntity<?> endTurn(String playerIdCookie) {
        Player player;

        try {
            Long playerId = Long.valueOf(playerIdCookie);
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
