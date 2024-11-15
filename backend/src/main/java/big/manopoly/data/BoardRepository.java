package big.manopoly.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import big.manopoly.models.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
