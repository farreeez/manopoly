package big.manopoly.data;

import org.springframework.data.jpa.repository.JpaRepository;

import big.manopoly.models.Utility;

public interface UtilityRepository extends JpaRepository<Utility, String> {
}