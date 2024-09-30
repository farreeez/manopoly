package big.manopoly.data;
import org.springframework.data.jpa.repository.JpaRepository;

import big.manopoly.models.Property;
import big.manopoly.utilities.PropertyName;

public interface PropertyRepository extends JpaRepository<Property, PropertyName> {
}
