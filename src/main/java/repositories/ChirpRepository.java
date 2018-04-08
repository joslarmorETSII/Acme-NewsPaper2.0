package repositories;

import domain.Chirp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp,Integer> {
}
