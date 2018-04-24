package services;

import domain.Advertisement;
import domain.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.AgentRepository;
import repositories.ArticleRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class AgentService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private AgentRepository agentRepository;

    // Supporting services ----------------------------------------------------



    // Constructors -----------------------------------------------------------

    public AgentService() { super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Agent create() {
        Agent res=null;

        res  = new Agent();

        return res;
    }

    public Agent save(Agent agent){
        Agent res = null;

        res= agentRepository.save(agent);
        return res;
    }

    public Collection<Agent> findAll(){
        Collection<Agent> res= null;
        res= this.agentRepository.findAll();
        return  res;
    }

    public Agent findOne(int agentId){
        Agent res= null;
        res= this.agentRepository.findOne(agentId);
        return  res;
    }

    public void delete(Agent agent){
        Assert.notNull(agent);

        this.agentRepository.delete(agent);
    }
}
