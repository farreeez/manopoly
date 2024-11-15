package big.manopoly.services;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import big.manopoly.data.PlayerRepository;
import big.manopoly.models.Player;

@RestController
@CrossOrigin
@RequestMapping("/players")
public class PlayerService {
    private final PlayerRepository repository;

    @Autowired
    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/createPlayer/{name}")
    public ResponseEntity<?> createPlayer(@PathVariable String name) {
        Player newPlayer = new Player();

        newPlayer.setName(name);

        newPlayer = repository.save(newPlayer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/getPlayer/{id}")
                .buildAndExpand(newPlayer.getId()).toUri();

        return ResponseEntity.created(location).body(newPlayer);
    }

    @GetMapping("/getPlayer/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable Long id) {
        Optional<Player> optionalPlayer = repository.findById(id);

        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();

            return ResponseEntity.ok().body(player);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
