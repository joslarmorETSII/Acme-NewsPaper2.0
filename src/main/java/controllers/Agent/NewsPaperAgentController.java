package controllers.Agent;

import controllers.AbstractController;
import domain.Advertisement;
import domain.Agent;
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

    @Autowired
    private AgentService agentService;

    public NewsPaperAgentController() { super();}

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Agent agent;

        result = new ModelAndView("newsPaper/listNewsPaperAdvertisement");
        agent = agentService.findByPrincipal();


        result.addObject("newsPapersWithAdds", newsPaperService.findNewsPaperPlacedAdvertisement(agent.getId()));
        result.addObject("newsPapersWithNoAdds",newsPaperService.newsPapersWithNoAdds());
        result.addObject("requestURI","/newsPaper/agent/list.do");

        return result;
    }

}
