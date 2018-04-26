package controllers;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AdvertisementService;
import services.AgentService;
import services.NewsPaperService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/advertisement")
public class AdvertisementController  extends AbstractController {

    //Services -------------------------------------------------------

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Advertisement advertisement= null;
        advertisement = this.advertisementService.create();
        result = this.createEditModelAndView(advertisement);

        return result;
    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int advertisementId) {
        final ModelAndView result;
        Advertisement advertisement;
        advertisement = this.advertisementService.findOne(advertisementId);
        Assert.notNull(advertisement);
        result = this.createEditModelAndView(advertisement);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Advertisement advertisement, BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(advertisement);
        else
            try {
                this.advertisementService.save(advertisement);
                result = new ModelAndView("redirect:/welcome/index.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(advertisement, "advertisement.commit.error");
            }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
    public ModelAndView edit(Advertisement advertisement) {
        ModelAndView result;

        try {
            advertisementService.delete(advertisement);
            result = new ModelAndView("redirect:/welcome/index.do");
        } catch (Throwable oops) {
            result = createEditModelAndView(advertisement, "advertisement.commit.error");
        }

        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Advertisement advertisement) {
        ModelAndView result;

        result = this.createEditModelAndView(advertisement, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Advertisement advertisement, final String messageCode) {
        ModelAndView result;
        Agent agent = agentService.findByPrincipal();

        Collection<NewsPaper> newsPapers = newsPaperService.findPublishedNewsPaper();

        result = new ModelAndView("advertisement/edit");
        result.addObject("advertisement", advertisement);
        result.addObject("newsPapers", newsPapers);
        result.addObject("actionUri", "advertisement/edit.do");
        result.addObject("message", messageCode);

        return result;
    }

}
