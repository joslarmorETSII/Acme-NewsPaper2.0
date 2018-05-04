package services;

import domain.SubscribeVolume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.SubscribeVolumeRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class SubscribeVolumeService {
    // Managed repository -----------------------------------------------------

    @Autowired
    private SubscribeVolumeRepository subscribeVolumeRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    // Constructors -----------------------------------------------------------

    public SubscribeVolumeService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public SubscribeVolume create(){
        SubscribeVolume res;

        res = new SubscribeVolume();
        res.setCustomer(customerService.findByPrincipal());

        return res;
    }

    public SubscribeVolume findOne(int id){
        return subscribeVolumeRepository.findOne(id);
    }

    public Collection<SubscribeVolume> findAll(){
        return subscribeVolumeRepository.findAll();
    }

    public void delete(SubscribeVolume subscribeNewsPaper){
        subscribeVolumeRepository.delete(subscribeNewsPaper);
    }

    public SubscribeVolume save(SubscribeVolume subscribeNewsPaper){
        subscribeNewsPaper.setMoment(new Date());
        return subscribeVolumeRepository.save(subscribeNewsPaper);
    }

    // Other business methods -------------------------------------------------

}
