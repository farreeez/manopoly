package big.manopoly.data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import big.manopoly.models.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {
}
