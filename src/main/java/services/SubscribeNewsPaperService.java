package services;

import domain.SubscribeNewsPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import repositories.SubscribeNewsPaperRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class SubscribeNewsPaperService  {
    // Managed repository -----------------------------------------------------

    @Autowired
    private SubscribeNewsPaperRepository subscribeNewsPaperRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    // Constructors -----------------------------------------------------------

    public SubscribeNewsPaperService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public SubscribeNewsPaper create(){
        SubscribeNewsPaper res;

        res = new SubscribeNewsPaper();
        res.setCustomer(customerService.findByPrincipal());

        return res;
    }

    public SubscribeNewsPaper findOne(int id){
        return subscribeNewsPaperRepository.findOne(id);
    }

    public Collection<SubscribeNewsPaper> findAll(){
        return subscribeNewsPaperRepository.findAll();
    }

    public void delete(SubscribeNewsPaper subscribeNewsPaper){
        subscribeNewsPaperRepository.delete(subscribeNewsPaper);
    }

    public SubscribeNewsPaper save(SubscribeNewsPaper subscribeNewsPaper){
        subscribeNewsPaper.setMoment(new Date());
        return subscribeNewsPaperRepository.save(subscribeNewsPaper);
    }

    // Other business methods -------------------------------------------------



}
