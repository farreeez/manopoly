package big.manopoly.data;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import big.manopoly.models.Board;
import big.manopoly.models.Property;
import big.manopoly.utils.PropertyType;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {
    List<Property> findByTypeAndBoard(PropertyType type, Board board);
}
