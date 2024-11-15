package big.manopoly.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import big.manopoly.models.Utility;

@Repository
public interface UtilityRepository extends JpaRepository<Utility, String> {
}