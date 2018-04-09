package repositories;

import domain.Administrator;
import domain.Article;
import domain.NewsPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

    @Query("select a from Administrator a where a.userAccount.id = ?1")
    Administrator findByUserAccountId(int userAccountId);

    // ########################  QUERIES LEVEL C  ################################

    // 1- The average and the standard deviation of newspapers created per user.
    @Query("select avg(u.newsPapers.size),sqrt(sum(u.newsPapers.size *u.newsPapers.size)/ count(u) - (avg(u.newsPapers.size) *avg(u.newsPapers.size))) from  User u")
    Collection<Double> avgStdOfNewspapersPerUser();

    // 2- The average and the standard deviation of articles written by writer.
   // @Query("select avg(u.newsPapers.size),sqrt(sum(u.newsPapers.size *u.newsPapers.size)/ count(u) - (avg(u.newsPapers.size) *avg(u.newsPapers.size))) from  User u")
   // Collection<Article> avgStdOfArticles();

    // 3- The average and the standard deviation of articles per newspaper.
    @Query("select avg(u.articles.size),sqrt(sum(u.articles.size *u.articles.size)/ count(u) - (avg(u.articles.size) *avg(u.articles.size))) from  NewsPaper u")
    Collection<Double> avgStdOfArticlesPerNewspaper();

    // 4- The newspapers that have at least 10% more articles than the average.
    @Query("select r from NewsPaper r where r.articles.size > (select avg(r1.articles.size)*1.1 from NewsPaper r1)")
    Collection<NewsPaper> newspapersWith10PercentMoreArticlesThanAvg();

    // 5- The newspapers that have at least 10% fewer articles than the average.

    @Query("select r from NewsPaper r where r.articles.size < (select avg(r1.articles.size)*1.1 from NewsPaper r1)")
    Collection<NewsPaper> newspapersWith10PercentFewerArticlesThanAvg();

    // 6- The ratio of users who have ever created a newspaper.

    @Query("select concat( 100 * ( select count(t) from User t where t.newsPapers is not empty )/ count(r), '%') from User r")
    Double ratioOfUsersThatCreatedNewspaper();

    // 7- The ratio of users who have ever written an article.

   // Double ratioOfUserCreatingArticle();

    // ########################  QUERIES LEVEL B  ################################

}
