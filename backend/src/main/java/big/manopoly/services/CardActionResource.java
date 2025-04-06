package big.manopoly.services;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.models.Board;
import big.manopoly.utils.BoardServicesUtility;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/cardActions")
public class CardActionResource {

    private final BoardRepository boardRepository;
    private final PlayerRepository playerRepository;
    
    public CardActionResource(BoardRepository boardRepository, PlayerRepository playerRepository) {
        this.boardRepository = boardRepository;
        this.playerRepository = playerRepository;
    }

    @PostMapping("/buyProperty")
    public ResponseEntity<?> buyProperty(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("endpoint works");
    }
}

