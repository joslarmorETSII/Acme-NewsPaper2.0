package repositories;

import domain.Article;
import domain.Customer;
import domain.NewsPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Repository
public interface NewsPaperRepository extends JpaRepository<NewsPaper,Integer> {

    @Query("select n from NewsPaper n where n.published =true")
    Collection<NewsPaper> findPublishedNewsPaper();

    @Query("select n from NewsPaper n where n.publisher.id=?1 AND n.published = FALSE")
    Collection<NewsPaper> findAllNewsPaperByUserAndNotPublished(int userId);

    @Query("select n from NewsPaper n where (n.title like %?1% or n.description like %?2%) and n.published=true")
    Collection<NewsPaper> searchNewspapers(String title,String description);

    @Query("select n from NewsPaper n where n.taboo =true")
    Collection<NewsPaper> findNewsPaperByTabooIsTrue();

    @Query("select n from NewsPaper n where n.published = true and n.modePrivate = true")
    Collection<NewsPaper> findPublishedAndPrivateNewsPaper();

    @Query("select n from NewsPaper n where n.published = true and n.modePrivate = false")
    Collection<NewsPaper> findPublishedAndNotPrivateNewsPaper();

    @Query("select distinct a.newsPaper from Advertisement a where a.agent.id=?1 ")
    Collection<NewsPaper> findNewsPaperPlacedAdvertisement(int agentId);

    @Query("select n from NewsPaper n where n.published= true and n.advertisements is empty  ")
    Collection<NewsPaper> newsPapersWithNoAdds();

    @Query("select n.customers from NewsPaper n where n.id=?1")
    Collection<Customer> customerOfNewsPaper(int newsPaperId);

}
