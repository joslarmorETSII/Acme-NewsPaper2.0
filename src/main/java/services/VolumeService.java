package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.VolumeRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class VolumeService {

    //Repositories -----------------------------------------------------------

    @Autowired
    private VolumeRepository volumeRepository;

    //Services ----------------------------------------------------------------

    @Autowired
    private UserService userService;

    //Constructores -----------------------------------------------------------

    public VolumeService() { super();
    }

    // Simple CRUD methods ----------------------------------------------------


    //create

    public Volume create(){
        Volume res= null;
        User user = this.userService.findByPrincipal();
        Collection<Customer> customers= new ArrayList<>();
        Collection<NewsPaper> newsPapers= new ArrayList<>();

        res= new Volume();
        res.setUser(user);
        res.setCustomers(customers);
        res.setNewsPapers(newsPapers);
        return res;
    }

    //save

    public Volume save (Volume volume){
        Volume res = null;
        Assert.isTrue(checkByPrincipal(volume));

        res= volumeRepository.save(volume);
        return res;

    }

    public Collection<Volume> findAll(){
        return volumeRepository.findAll();
    }

    public Volume findOne(int volumeId){
        Volume res= null;
        res= volumeRepository.findOne(volumeId);
        return res;
    }

    public Volume findOneToEdit(int volumeId){
        Volume res= null;
        res= volumeRepository.findOne(volumeId);
        Assert.isTrue(checkByPrincipal(res));
        return res;
    }

    //Anthiliaries methodes

    public boolean checkByPrincipal(Volume volume) {
        Boolean res = null;
        User principal = null;

        res = false;
        principal = this.userService.findByPrincipal();

        if (volume.getUser().equals(principal))
            res = true;

        return res;
    }
}
