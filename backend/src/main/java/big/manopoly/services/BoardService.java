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
import big.manopoly.data.PlayerRepository;
import big.manopoly.dtos.BoardDTO;
import big.manopoly.models.Board;
import big.manopoly.models.Player;
import big.manopoly.utils.BoardServicesUtility;
import big.manopoly.utils.Mapper;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/board")
public class BoardService {

    private final BoardRepository boardRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, PlayerRepository playerRepository) {
        this.boardRepository = boardRepository;
        this.playerRepository = playerRepository;
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
    public ResponseEntity<?> joinBoard(@CookieValue(value = "playerId", defaultValue = "") String cookie, @PathVariable Long id) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (!optionalBoard.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Board board = optionalBoard.get();

        return BoardServicesUtility.addPlayerFromCookie(cookie, board, playerRepository, boardRepository);
    }

    @DeleteMapping("/leaveBoard")
    public ResponseEntity<?> leaveBoard(@CookieValue(value = "playerId", defaultValue = "") String cookie, HttpServletResponse response) {
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
}
