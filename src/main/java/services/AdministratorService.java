package services;

import domain.Administrator;
import domain.Article;
import domain.NewsPaper;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class AdministratorService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private AdministratorRepository administratorRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserAccountService userAccountService;

    // Constructors -----------------------------------------------------------

    public AdministratorService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------


    public Administrator save(final Administrator administrator) {

        Assert.notNull(administrator);

        Administrator result;

        result = this.administratorRepository.save(administrator);

        return result;
    }

    // Other business methods -------------------------------------------------

    public Administrator findByPrincipal() {
        Administrator result;
        UserAccount userAccount;

        userAccount = LoginService.getPrincipal();
        result = this.findByUserAccount(userAccount);

        return result;
    }

    public Administrator findByUserAccount(final UserAccount userAccount) {

        Administrator result;
        result = this.administratorRepository.findByUserAccountId(userAccount.getId());
        return result;
    }

    public Collection<Double> avgStdOfNewspapersPerUser(){

        Collection<Double> result;
        result = this.administratorRepository.avgStdOfNewspapersPerUser();
        return result;
    }

    public Collection<Article> avgStdOfArticles(){

        return this.administratorRepository.avgStdOfArticles();
    }

    public Collection<Double> avgStdOfArticlesPerNewspaper(){

        return this.administratorRepository.avgStdOfArticlesPerNewspaper();
    }

    public Collection<NewsPaper> newspapersWith10PercentMoreArticlesThanAvg(){

        return this.administratorRepository.newspapersWith10PercentMoreArticlesThanAvg();
    }

    public Collection<NewsPaper> newspapersWith10PercentFewerArticlesThanAvg(){

        return this.administratorRepository.newspapersWith10PercentFewerArticlesThanAvg();
    }

    public Double ratioOfUsersThatCreatedNewspaper(){

        return this.administratorRepository.ratioOfUsersThatCreatedNewspaper();
    }

    public Double ratioOfUserCreatingArticle(){

        return this.administratorRepository.ratioOfUserCreatingArticle();
    }

    public Double avgFollowUpsPerArticle(){

        return this.administratorRepository.avgFollowUpsPerArticle();
    }

    public Double avgFollowUpsPerArticleAfter1weekNewspaprerPublished(){

        return this.administratorRepository.avgFollowUpsPerArticleAfter1weekNewspaprerPublished();
    }

    public Double avgFollowUpsPerArticleAfter2weekNewspaprerPublished(){

        return this.administratorRepository.avgFollowUpsPerArticleAfter2weekNewspaprerPublished();
    }

    public Collection<Double> avgStdChirpsPerUser(){

        return this.administratorRepository.avgStdChirpsPerUser();
    }

    public Collection<User> ratioUsersWith75PercentMoreChirpsPostedThanAVG(){

        return this.administratorRepository.ratioUsersWith75PercentMoreChirpsPostedThanAVG();
    }

    public Double ratioPublicVSPrivateNewspapers(){

        return this.administratorRepository.ratioPublicVSPrivateNewspapers();
    }

    public Double avgArticlesPerNewsPapersPrivate(){

        return this.administratorRepository.avgArticlesPerNewsPapersPrivate();
    }

    public Double avgArticlesPerNewsPapersPublic(){

        return this.administratorRepository.avgArticlesPerNewsPapersPublic();
    }

    public Double ratioPrivateNewsPapersVsCustomers(){

        return this.administratorRepository.ratioPrivateNewsPapersVsCustomers();
    }

    public Double ratioPrivateNewsPapersVsPublicPerPublisher(){

        return this.administratorRepository.ratioPrivateNewsPapersVsPublicPerPublisher();
    }
}
