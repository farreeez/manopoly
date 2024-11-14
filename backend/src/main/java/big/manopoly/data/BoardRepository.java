package big.manopoly.data;

import org.springframework.data.jpa.repository.JpaRepository;

import big.manopoly.models.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
