package big.manopoly.controllers;

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

import big.manopoly.services.BoardService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/createBoard")
    public ResponseEntity<?> createBoard(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return boardService.createBoard(cookie);
    }

    @GetMapping("/getBoard/{id}")
    public ResponseEntity<?> getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PostMapping("/joinBoard/{id}")
    public ResponseEntity<?> joinBoard(@CookieValue(value = "playerId", defaultValue = "") String cookie,
            @PathVariable Long id) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return boardService.joinBoard(cookie, id);
    }

    @DeleteMapping("/leaveBoard")
    public ResponseEntity<?> leaveBoard(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return boardService.leaveBoard(cookie);
    }

    @GetMapping("/getBoardSquare/{id}")
    public ResponseEntity<?> getBoardSquare(@PathVariable String id) {
        return boardService.getBoardSquare(id);
    }

    @GetMapping("/subscribeToBoard/{id}")
    public void subscribeToBoard(@PathVariable Long id, HttpServletRequest request) {
        boardService.subscribeToBoard(id, request);
    }

    @GetMapping("/processSubs/{id}")
    public ResponseEntity<?> processSubs(@PathVariable Long id) {
        return boardService.processSubs(id);
    }

    @PostMapping("/chooseColour/{colourId}")
    public ResponseEntity<?> chooseColour(@CookieValue(value = "playerId", defaultValue = "") String cookie,
            @PathVariable int colourId) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return boardService.chooseColour(cookie, colourId);
    }

    @PostMapping("/rollDice")
    public ResponseEntity<?> rollDice(@CookieValue(value = "playerId", defaultValue = "") String cookie) throws Exception {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().body("player cookie is empty.");
        }
        return boardService.rollDice(cookie);
    }

    @PostMapping("/endTurn")
    public ResponseEntity<?> endTurn(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().body("player cookie is empty.");
        }
        return boardService.endTurn(cookie);
    }
}
