package big.manopoly.data;
import org.springframework.data.jpa.repository.JpaRepository;

import big.manopoly.models.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
