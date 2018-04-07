package repositories;

import domain.NewsPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NewsPaperRepository extends JpaRepository<NewsPaper,Integer> {

    @Query("select n from NewsPaper n where n.published =true AND n.taboo = false")
    Collection<NewsPaper> findPublishedNewsPaper();

    @Query("select n,c from NewsPaper n join n.customers c where c.id = 27 AND n.modePrivate = TRUE")
    Collection<NewsPaper>findNewsPapersPrivate(int customerId);

    @Query("select n from NewsPaper n where n.publisher.id=?1 AND n.published = FALSE")
    Collection<NewsPaper> findAllNewsPaperByUserAndNotPublished(int userId);

}
