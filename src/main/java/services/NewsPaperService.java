package services;

import domain.*;
import forms.CustomerForm;
import forms.SubscribeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import repositories.NewsPaperRepository;
import security.Authority;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class NewsPaperService {
    // Managed repository -----------------------------------------------------

    @Autowired
    private NewsPaperRepository newsPaperRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ConfigurationService configurationService;

    // Constructors -----------------------------------------------------------

    public NewsPaperService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public NewsPaper create() {
        NewsPaper res= null;
        User publisher= null;
        Collection<Article> articles= new ArrayList<>();
        Collection<Customer> customers= new ArrayList<>();
        publisher=userService.findByPrincipal();
        res= new NewsPaper();
        res.setPublisher(publisher);
        res.setArticles(articles);
        res.setCustomers(customers);
        return res;
    }

    public NewsPaper save(NewsPaper newsPaper){
        NewsPaper res= null;
        Assert.isTrue(checkByPrincipal(newsPaper));
        if(isTabooNewsPaper(newsPaper)){
            newsPaper.setTaboo(true);
        }
        res= newsPaperRepository.save(newsPaper);
        return res;
    }

    public Collection<NewsPaper> findAll(){
        Collection<NewsPaper> res= null;
        res= this.newsPaperRepository.findAll();
        return  res;
    }

    public NewsPaper findOne(int newsPaperId){
        NewsPaper res= null;
        res= this.newsPaperRepository.findOne(newsPaperId);
        return  res;
    }
    public NewsPaper findOneToEdit(int newsPaperId){
        NewsPaper res= null;

        res= this.newsPaperRepository.findOne(newsPaperId);
        Assert.isTrue(!res.getPublished());
        Assert.isTrue(checkByPrincipal(res) || checkByPrincipalAdmin(res));
        return  res;
    }

    public void delete(NewsPaper newsPaper){
        Assert.notNull(newsPaper);
        Assert.isTrue(checkByPrincipalAdmin(newsPaper) || checkByPrincipal(newsPaper));
        Collection<Customer> customers =newsPaper.getCustomers();
        if(customers.size()>0) {
            for(Customer c : customers){
                c.getNewsPapers().remove(newsPaper);
                customerService.save(c);
            }
        }

        this.articleService.deleteAll(newsPaper.getArticles());
        this.newsPaperRepository.delete(newsPaper);
    }

    // Other business methods -------------------------------------------------

    public void findOneToPublish(NewsPaper newsPaper){
        Collection<Article> articles= newsPaper.getArticles();
        Assert.isTrue(!newsPaper.getTaboo());
        for(Article a:articles){
            Assert.isTrue(!a.getTaboo());
            if(a.getFinalMode() && !a.getTaboo()){
                newsPaper.setPublished(true);
                newsPaper.setPublicationDate(new Date());
                a.setMoment(new Date());
            }else{
                newsPaper.setPublished(false);
            }
        }
    }

    public boolean checkByPrincipal(NewsPaper newsPaper) {
        Boolean res = null;
        User principal = null;

        res = false;
        principal = this.userService.findByPrincipal();

        if (newsPaper.getPublisher().equals(principal))
            res = true;

        return res;
    }

    public boolean checkByPrincipalAdmin(NewsPaper newsPaper){
        Boolean res= false;
        Administrator administrator = administratorService.findByPrincipal();
        if(administrator!=null) {
            Collection<Authority> authorities = administrator.getUserAccount().getAuthorities();
            String authority = authorities.toArray()[0].toString();
            res = authority.equals("ADMINISTRATOR");
        }
        return res;

    }

    public Collection<NewsPaper> findNewsPapersPrivate(int customerId){

        Collection<NewsPaper> result;
        result = newsPaperRepository.findNewsPapersPrivate(customerId);
        return result;
    }

    public Collection<NewsPaper> findPublishedNewsPaper(){
        return newsPaperRepository.findPublishedNewsPaper();
    }

    public Collection<NewsPaper> findAllNewsPaperByUserAndNotPublished(int userId) {
        return this.newsPaperRepository.findAllNewsPaperByUserAndNotPublished(userId);
    }

    private boolean isTabooNewsPaper(final NewsPaper newsPaper) {
        boolean result = false;
        Pattern p;
        Matcher isAnyMatcherTitle;
        Matcher isAnyMatcherDescription;

        p = this.tabooWords();
        isAnyMatcherTitle = p.matcher(newsPaper.getTitle());
        isAnyMatcherDescription = p.matcher(newsPaper.getDescription());

        if (isAnyMatcherTitle.find() || isAnyMatcherDescription.find())
            result = true;

        return result;
    }

    public Pattern tabooWords() {
        Pattern result;
        List<String> tabooWords;

        final Collection<String> taboolist = this.configurationService.findAll().iterator().next().getTabooWords();
        tabooWords = new ArrayList<>(taboolist);

        String str = ".*\\b(";
        for (int i = 0; i <= tabooWords.size(); i++)
            if (i < tabooWords.size())
                str += tabooWords.get(i) + "|";
            else
                str += tabooWords.iterator().next() + ")\\b.*";

        result = Pattern.compile(str, Pattern.CASE_INSENSITIVE);

        return result;
    }

    public Collection<NewsPaper> newsPapersTaboo(){
        Collection<NewsPaper> res= new ArrayList<>();
        Collection<NewsPaper> newsPapers= newsPaperRepository.findAll();
        for(NewsPaper n:newsPapers){
            if(isTabooNewsPaper(n)) {
                res.add(n);
                n.setTaboo(true);
                newsPaperRepository.save(n);
            }
            }
        return res;
    }

    public Collection<NewsPaper> searchNewspapers(String keyword) {
        return newsPaperRepository.searchNewspapers(keyword,keyword);
    }

}
