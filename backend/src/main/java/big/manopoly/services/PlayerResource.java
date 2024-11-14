package big.manopoly.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/players")
public class PlayerResource {
    @GetMapping("/joinGame")
    public ResponseEntity<?> joinGame() {
        return ResponseEntity.ok().body("joined");
    }
}
