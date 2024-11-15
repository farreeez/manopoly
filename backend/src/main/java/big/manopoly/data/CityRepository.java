package big.manopoly.data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import big.manopoly.models.City;

@Repository
public interface CityRepository extends JpaRepository<City, String> {
}
