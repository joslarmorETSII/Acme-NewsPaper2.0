package repositories;

import domain.Administrator;
import domain.Article;
import domain.NewsPaper;
import domain.User;
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
    // TODO
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
    // TODO
   // Double ratioOfUserCreatingArticle();

    // ########################  QUERIES LEVEL B  ################################


    // 8- The average number of follow-ups per article.
   // @Query("select avg(a.followUps.size) from Article a ")
   // Double avgFollowUpsPerArticle();

    // 9- The average number of follow-ups per article up to one week after the corresponding newspaper?s been published.
   // @Query("select avg(a.followUps.size) from Article a where CURRENT_TIMESTAMP - a.newsPaper.publicationDate >=604800")
    //Double avgFollowUpsPerArticleAfter1weekNewspaprerPublished();

    // 10- The average number of follow-ups per article up to two weeks after the corresponding newspaper?s been published.

    // 11- The average and the standard deviation of the number of chirps per user.
    //@Query("select avg(u.chirps.size),sqrt(sum(u.chirps.size *u.chirps.size)/ count(u) - (avg(u.chirps.size) *avg(u.chirps.size))) from  User u")
    //Collection<Double> avgStdChirpsPerUser();

    // 12- The ratio of users who have posted above 75% the average number of chirps per user.
    // TODO
    // @Query("select r from User r join r.chirps rc where count(select ) > (select count(rc.posted= true)*1.1 from User r1)")
    //Collection<User> ratioUsersWith75PercentMoreChirpsPostedThanAVG();


    // (select avg(r1.articles.size)*1.1 from NewsPaper r1)
    //select count(c),u from Chirp c, User u where c.posted=true and c.user = u group by u);
}
