package big.manopoly.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import big.manopoly.models.BoardSquare;

@Repository
public interface BoardSquareRepository extends JpaRepository<BoardSquare, String> {
}
