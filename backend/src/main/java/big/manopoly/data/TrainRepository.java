package big.manopoly.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import big.manopoly.models.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train, String> {
}
