package repositories;

import domain.NewsPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsPaperRepository extends JpaRepository<NewsPaper,Integer> {
}
