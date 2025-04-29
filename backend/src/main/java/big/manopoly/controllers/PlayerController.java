package big.manopoly.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import big.manopoly.services.PlayerService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/players")
public class PlayerController {
    
    private final PlayerService playerService;
    
    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    
    @PostMapping("/createPlayer/{name}")
    public ResponseEntity<?> createPlayer(@PathVariable String name, HttpServletResponse response)
            throws JsonProcessingException {
        return playerService.createPlayer(name, response);
    }
    
    @GetMapping("/checkCookie")
    public ResponseEntity<?> checkCookie(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
        return playerService.checkCookie(cookie);
    }
    
    @GetMapping("/getPlayer/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    @PostMapping("/payJailFine")
    public ResponseEntity<?> payJailFine(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
        return playerService.payJailFine(cookie);
    }

}