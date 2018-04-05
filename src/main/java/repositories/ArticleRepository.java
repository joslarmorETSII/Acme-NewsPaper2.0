package repositories;

import domain.Article;
import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {

    @Query("select a from NewsPaper n join n.articles a where n.publisher=?1")
    Collection<Article> findArticlesByUser(User user);

}
