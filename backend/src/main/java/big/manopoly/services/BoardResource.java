package big.manopoly.services;

import java.io.PrintWriter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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
import big.manopoly.models.Board;
import big.manopoly.models.BoardSquare;
import big.manopoly.models.Player;
import big.manopoly.utils.BoardServicesUtility;
import big.manopoly.utils.Mapper;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

        BoardDTO boardDTO = Mapper.toBoardDTO(board);

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
        System.out.println("hello");

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

        BoardServicesUtility.removePlayer(player, boardRepository);

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
        AsyncContext sub = request.startAsync();
        BoardSubscriptionManager.instance().addSubscription(id, sub);
    }

    @GetMapping("/processSubs/{id}")
    public ResponseEntity<?> processSubs(@PathVariable Long id) {
        BoardSubscriptionManager.instance().processSubsFor(id, boardRepository);
        return ResponseEntity.ok().build();
    }
}
