package big.manopoly.services;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import big.manopoly.data.PlayerRepository;
import big.manopoly.models.Player;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/players")
public class PlayerService {
    private final PlayerRepository repository;

    @Autowired
    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/createPlayer/{name}")
    public ResponseEntity<?> createPlayer(@PathVariable String name, HttpServletResponse response) throws JsonProcessingException {
        Player newPlayer = new Player();

        newPlayer.setName(name);

        newPlayer = repository.save(newPlayer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/getPlayer/{id}")
                .buildAndExpand(newPlayer.getId()).toUri();

        ResponseCookie cookie = ResponseCookie.from("playerId").value(newPlayer.getId().toString())
                .maxAge(Duration.ofMinutes(360)).httpOnly(true).secure(true).path("/").build();

        return ResponseEntity.created(location).header("Set-Cookie", cookie.toString()).header("Access-Control-Allow-Credentials", "true").body(newPlayer);
    }

    @GetMapping("/checkCookie")
    public ResponseEntity<?> checkCookie(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
        System.out.println(cookie);

        return ResponseEntity.ok().header("Access-Control-Allow-Credentials", "true").body(cookie);
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
