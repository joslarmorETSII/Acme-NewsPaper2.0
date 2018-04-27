package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.AdvertisementRepository;
import repositories.AgentRepository;
import repositories.ArticleRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class AdvertisementService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private AdvertisementRepository advertisementRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private AgentService agentService;

    @Autowired
    private NewsPaperService    newsPaperService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ActorService actorService;

    // Constructors -----------------------------------------------------------

    public AdvertisementService() { super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Advertisement create() {
        Advertisement res=null;
        Agent agent = null;
        agent = agentService.findByPrincipal();

        res  = new Advertisement();
        res.setNewsPapers(new ArrayList<NewsPaper>());
        res.setAgent(agent);

        return res;
    }

    public Advertisement save(Advertisement advertisement){
        Advertisement res = null;
        Assert.isTrue(checkByPrincipal(advertisement));

        if(isTabooAdvertisement(advertisement)){
            advertisement.setTaboo(true);
        }
        res= advertisementRepository.save(advertisement);
        return res;
    }

    public Collection<Advertisement> findAll(){
        Collection<Advertisement> res= null;
        res= this.advertisementRepository.findAll();
        return  res;
    }

    public Advertisement findOne(int advertisementId){
        Advertisement res= null;
        res= this.advertisementRepository.findOne(advertisementId);
        return  res;
    }

    public void delete(Advertisement advertisement){
        Assert.notNull(advertisement);
        Collection<NewsPaper> newsPapers = newsPaperService.findAll();

        for(NewsPaper n: newsPapers){
            n.getAdvertisements().remove(advertisement);
        }
        this.advertisementRepository.delete(advertisement);
    }

    // Other business methods -------------------------------------------------

    public boolean checkByPrincipal(Advertisement advertisement) {
        Boolean res = null;
        Agent principal = null;

        res = false;
        principal = this.agentService.findByPrincipal();

        if (advertisement.getAgent().equals(principal))
            res = true;

        return res;
    }

    private boolean isTabooAdvertisement(final Advertisement advertisement) {
        boolean result = false;
        Pattern p;
        Matcher isAnyMatcherTitle;

        p = this.tabooWords();
        isAnyMatcherTitle = p.matcher(advertisement.getTitle());

        if (isAnyMatcherTitle.find() )
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

    public Collection<Advertisement> findTabooAdvertisement() {
        Assert.isTrue(actorService.isAdministrator());
        return advertisementRepository.findTabooAdvertisement();
    }


}
