package big.manopoly.data;
import org.springframework.data.jpa.repository.JpaRepository;

import big.manopoly.models.City;
import big.manopoly.utilities.CityName;

public interface CityRepository extends JpaRepository<City, CityName> {
}
