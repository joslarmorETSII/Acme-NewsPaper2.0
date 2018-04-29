package controllers.Administrator;

import controllers.AbstractController;
import domain.Chirp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ChirpService;

import java.util.Collection;

@Controller
@RequestMapping("/chirp/administrator")
public class ChirpAdministratorController extends AbstractController{

    // Services --------------------------------------------

    @Autowired
    private ChirpService chirpService;

    // Constructor --------------------------------------------


    public ChirpAdministratorController() { super();}

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Chirp> tabooChirps;
        Collection<Chirp> allChirps;

        result = new ModelAndView("chirp/list");
        tabooChirps = chirpService.findTabooChirps();
        allChirps = chirpService.findPublishedChirps();

        result.addObject("allChirps", allChirps);
        result.addObject("chirps",tabooChirps);

        return result;
    }

    // Delete --------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int chirpId) {
        ModelAndView result;
        Chirp chirp;

        try {
            chirp = chirpService.findOne(chirpId);
            chirpService.delete(chirp);
            result = new ModelAndView("redirect:list.do");
        } catch (Throwable oops) {
            result = new ModelAndView("misc/panic");
        }

        return result;
    }


}
