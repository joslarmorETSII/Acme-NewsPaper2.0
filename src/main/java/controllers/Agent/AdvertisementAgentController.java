package controllers.Agent;

import controllers.AbstractController;
import controllers.Administrator.AdvertisementAdministratorController;
import domain.*;
import forms.RegisterAdvertisementForm;
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
import services.CreditCardService;
import services.NewsPaperService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/advertisement/agent")
public class AdvertisementAgentController extends AbstractController {

    //Services -------------------------------------------------------

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private NewsPaperService newsPaperService;

    public AdvertisementAgentController() {
        super();
    }


    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;

        result = new ModelAndView("advertisement/registerAdvertisementForm");
        RegisterAdvertisementForm registerAdvertisementForm;
        registerAdvertisementForm = new RegisterAdvertisementForm();
        result.addObject("registerAdvertisementForm", registerAdvertisementForm);
        result.addObject("newsPapers", newsPaperService.findPublishedNewsPaper());

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
    public ModelAndView save(@Valid RegisterAdvertisementForm registerAdvertisementForm, BindingResult binding) {
        ModelAndView result;
        Agent agent;
        NewsPaper newsPaper;
        try {
            Advertisement advertisement = agentService.reconstructRegisterAdvertisement(registerAdvertisementForm, binding);

            if (binding.hasErrors())
                result = createEditModelAndView2(registerAdvertisementForm);
            else {
                result = new ModelAndView("redirect: list.do");
                agent = agentService.findByPrincipal();
                agent.getAdvertisements().add(advertisement);
                advertisement.setAgent(agent);
                newsPaper = registerAdvertisementForm.getNewsPaper();
                newsPaper.getAdvertisements().add(advertisement);
                newsPaperService.save(newsPaper);
                advertisementService.save(advertisement);

            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView2(registerAdvertisementForm, "general.commit.error");
        }
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

    private ModelAndView createEditModelAndView2(RegisterAdvertisementForm registerAdvertisementForm) {
        return createEditModelAndView2(registerAdvertisementForm, null);
    }

    private ModelAndView createEditModelAndView2(RegisterAdvertisementForm registerAdvertisementForm,String messageCode) {


    }
}