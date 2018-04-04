package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ArticleRepository;
import repositories.NewsPaperRepository;
import security.Authority;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class ArticleService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private ArticleRepository articleRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private FollowUpService followUpService;

    // Constructors -----------------------------------------------------------

    public ArticleService() { super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Article create(){
        Article res= null;
        User publisher =null;

        publisher= userService.findByPrincipal();
        Collection<FollowUp> followUps= new ArrayList<>();
        res= new Article();
        res.getNewsPaper().setPublisher(publisher);
        res.setFollowUps(followUps);
        return res;
    }

    public Article save (Article article){
        Article res= null;
        Assert.notNull(article);
        Assert.isTrue(checkByPrincipal(article));
        Assert.isTrue(!article.getNewsPaper().getPublished());
        Assert.isTrue(!article.getFinalMode());

        NewsPaper newsPaper=article.getNewsPaper();
        newsPaper.getArticles().add(article);
        res= articleRepository.save(article);
        return res;

    }

    public Collection<Article> findAll(){
        return articleRepository.findAll();
    }

    public Article findOne(int articleId){
        Article res= null;
        res= articleRepository.findOne(articleId);
        return res;
    }

    public void delete(Article article){
        Assert.isTrue(checkByPrincipalAdmin(article));
        articleRepository.delete(article);
        Collection<FollowUp> followUps = article.getFollowUps();
        followUpService.deleteAll(followUps);


    }

    // Other business methods -------------------------------------------------

    public boolean checkByPrincipal(Article article) {
        Boolean res = null;
        User principal = null;

        res = false;
        principal = this.userService.findByPrincipal();

        if (article.getNewsPaper().getPublisher().equals(principal))
            res = true;

        return res;
    }

    public boolean checkByPrincipalAdmin(Article article){
        Boolean res= false;
        Administrator administrator = administratorService.findByPrincipal();
        Collection<Authority> authorities= administrator.getUserAccount().getAuthorities();
        String authority= authorities.toArray()[0].toString();
        res= authority.equals("ADMINISTRATOR");
        return res;

    }

}
