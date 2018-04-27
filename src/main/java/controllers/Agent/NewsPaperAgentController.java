package controllers.Agent;

import controllers.AbstractController;
import domain.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.AdvertisementService;
import services.AgentService;
import services.NewsPaperService;

import java.util.Collection;

@Controller
@RequestMapping("/newsPaper/agent")
public class NewsPaperAgentController extends AbstractController {

    //Services -------------------------------------------------------

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private AdvertisementService advertisementService;

    public NewsPaperAgentController() { super();}

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Advertisement> tabooAdvertisement;
        Collection<Advertisement> allAdvertisement;

        result = new ModelAndView("advertisement/list");
       // tabooAdvertisement = advertisementService.findTabooAdvertisement();
        //allAdvertisement = advertisementService.findAll();

       // result.addObject("advertisements", allAdvertisement);
        //result.addObject("tabooAdvertisements",tabooAdvertisement);

        return result;
    }

}
