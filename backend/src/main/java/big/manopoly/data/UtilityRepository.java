package big.manopoly.data;

import org.springframework.data.jpa.repository.JpaRepository;

import big.manopoly.models.Utility;
import big.manopoly.utilities.UtilityName;

public interface UtilityRepository extends JpaRepository<Utility, UtilityName> {
}