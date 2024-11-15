package big.manopoly.data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import big.manopoly.models.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
