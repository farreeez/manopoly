package big.manopoly.services;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.dtos.PlayerDTO;
import big.manopoly.models.Board;
import big.manopoly.models.Player;
import big.manopoly.utils.Mapper;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, BoardRepository boardRepository) {
        this.playerRepository = playerRepository;
        this.boardRepository = boardRepository;
    }

    public ResponseEntity<?> createPlayer(String name, HttpServletResponse response)
            throws JsonProcessingException {
        Player newPlayer = new Player();
        newPlayer.setName(name);
        newPlayer = playerRepository.save(newPlayer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/getPlayer/{id}")
                .buildAndExpand(newPlayer.getId())
                .toUri();

        ResponseCookie cookie = ResponseCookie.from("playerId")
                .value(newPlayer.getId().toString())
                .maxAge(Duration.ofMinutes(360))
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();

        return ResponseEntity.created(location)
                .header("Set-Cookie", cookie.toString())
                .body(newPlayer);
    }

    public ResponseEntity<?> checkCookie(String cookie) {
        if (cookie.length() > 0) {
            try {
                Long id = Long.valueOf(cookie);
                Player player = playerRepository.getReferenceById(id);
                return ResponseEntity.ok().body(Mapper.toPlayerDTO(player));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Invalid playerId cookie format");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.ok().body(cookie);
        }
    }

    public ResponseEntity<?> getPlayer(Long id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);

        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            PlayerDTO playerDTO = Mapper.toPlayerDTO(player);
            return ResponseEntity.ok().body(playerDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> payJailFine(String playerIdCookie) {
        Player player;

        try {
            Long playerId = Long.valueOf(playerIdCookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid playerId cookie value");
        }

        Board board = player.getBoard();

        if (board == null) {
            return ResponseEntity.badRequest().body("player cannot pay the fine as they have no board.");
        }

        if (player.isFree()) {
            return ResponseEntity.badRequest().body("Player cannot pay jail fine as they are already free.");
        }

        if (!board.getPlayerWithCurrentTurn().equals(player)) {
            return ResponseEntity.badRequest().body("Player cannot pay jail fine as it is not their turn.");
        }

        if (player.getMoney() < 50) {
            return ResponseEntity.badRequest().body("Player cannot afford to pay the fine.");
        }

        player.pay(50);
        player.setFree(true);
        player.resetJailCounter();

        playerRepository.save(player);

        board.saveBoard(boardRepository, false);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> useGetOutOfJailCard(String playerIdCookie) {
        Player player;

        try {
            Long playerId = Long.valueOf(playerIdCookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid playerId cookie value");
        }

        Board board = player.getBoard();

        if (board == null) {
            return ResponseEntity.badRequest().body("player cannot use the jail card as they have no board.");
        }

        if (player.isFree()) {
            return ResponseEntity.badRequest().body("Player cannot use the jail card as they are already free.");
        }

        if (!board.getPlayerWithCurrentTurn().equals(player)) {
            return ResponseEntity.badRequest().body("Player cannot use the jail card as it is not their turn.");
        }

        if (player.getJailCards() <= 0) {
            return ResponseEntity.badRequest().body("The player has no jail cards.");
        }

        player.decrementGetOutOfJailCard();
        player.setFree(true);
        player.resetJailCounter();

        playerRepository.save(player);

        board.saveBoard(boardRepository, false);
        return ResponseEntity.ok().build();
    }
}