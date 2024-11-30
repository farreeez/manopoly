package big.manopoly.services;

import java.net.URI;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.dtos.BoardDTO;
import big.manopoly.models.Board;
import big.manopoly.models.Player;
import big.manopoly.utils.Mapper;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<?> checkCookie(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
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

        // creating a new board should remove player from previous board
        if (player.getBoard() != null) {
            player.getBoard().removePlayer(player);
            boardRepository.save(player.getBoard());
        }

        Board board = new Board();

        boolean checkAdded = board.addPlayer(player);

        if (!checkAdded) {
            return ResponseEntity.badRequest().build();
        }

        boardRepository.save(board);

        BoardDTO BoardDTO = Mapper.toBoardDTO(board);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/getBoard/{id}")
                .buildAndExpand(board.getId()).toUri();

        return ResponseEntity.created(location)
                .header("Access-Control-Allow-Credentials", "true").body(BoardDTO);
    }

    @GetMapping("/getBoard/{id}")
    public ResponseEntity<?> getBoard(@PathVariable Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (!optionalBoard.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Board board = optionalBoard.get();

        return ResponseEntity.ok().body(board);
    }
}
