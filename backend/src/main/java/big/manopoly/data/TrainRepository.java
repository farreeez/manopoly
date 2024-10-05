package big.manopoly.data;

import org.springframework.data.jpa.repository.JpaRepository;

import big.manopoly.models.Train;

public interface TrainRepository extends JpaRepository<Train, String> {
}
