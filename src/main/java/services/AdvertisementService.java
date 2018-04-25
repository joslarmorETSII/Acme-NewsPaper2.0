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

@Service
@Transactional
public class AdvertisementService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private AdvertisementRepository advertisementRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private AgentService agentService;

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
}
