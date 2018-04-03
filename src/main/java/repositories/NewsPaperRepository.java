package repositories;

import domain.NewsPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NewsPaperRepository extends JpaRepository<NewsPaper,Integer> {




    @Query("select n from Newspaper n where n.userAccount.id = ?1 AND n.modePrivate = true")
    Collection<NewsPaper>findNewsPapersPrivate(int customerId);
}
