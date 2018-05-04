package repositories;

import domain.SubscribeVolume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeVolumeRepository extends JpaRepository<SubscribeVolume,Integer> {
}
