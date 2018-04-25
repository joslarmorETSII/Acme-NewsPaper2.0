package controllers.User;

import controllers.AbstractController;
import domain.Article;
import domain.NewsPaper;
import domain.User;
import domain.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ArticleService;
import services.NewsPaperService;
import services.UserService;
import services.VolumeService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/volume/user")
public class VolumeUserController extends AbstractController{
    // Services --------------------------------------------

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private UserService userService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Constructor --------------------------------------------

    public VolumeUserController() {
        super();
    }

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Volume volume = null ;
        volume = this.volumeService.create();
        result = this.createEditModelAndView(volume);

        return result;
    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int volumeId) {
        final ModelAndView result;
        Volume volume;
        volume = this.volumeService.findOneToEdit(volumeId);
        Assert.notNull(volume);
        result = this.createEditModelAndView(volume);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(Volume volumePruned, final BindingResult binding) {
        ModelAndView result;

        Volume volume = this.volumeService.reconstructS(volumePruned,binding);

        if (binding.hasErrors())
            result = this.createEditModelAndView(volume);
        else
            try {
                this.volumeService.save(volume);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(volume, "volume.commit.error");
            }
        return result;
    }


    @RequestMapping(value = "/addNewsPaper", method = RequestMethod.GET)
    public ModelAndView editN(@RequestParam int volumeId) {
        final ModelAndView result;
        Volume volume;
        volume = this.volumeService.findOneToEdit(volumeId);
        Assert.notNull(volume);
        result = this.createEditModelAndView2(volume);
        return result;
    }
    @RequestMapping(value = "/addNewsPaper", method = RequestMethod.POST, params = "save")
    public ModelAndView saveN(@Valid Volume volume, final BindingResult binding) {
        ModelAndView result;


        if (binding.hasErrors())
            result = this.createEditModelAndView2(volume);
        else
            try {
                this.volumeService.save(volume);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView2(volume, "volume.commit.error");
            }
        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        User user;
        Collection<Volume> volumes = null;

        user = userService.findByPrincipal();
        volumes = user.getVolumes();

        result = new ModelAndView("volume/list");
        result.addObject("volumes", volumes);
        result.addObject("user", user);
        result.addObject("requestURI","volume/user/list.do");

        return result;

    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Volume volume) {
        ModelAndView result;

        result = this.createEditModelAndView(volume, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Volume volume, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("volume/edit");
        result.addObject("volume", volume);
        result.addObject("actionUri","volume/user/edit.do");
        result.addObject("message", messageCode);

        return result;
    }
    protected ModelAndView createEditModelAndView2(final Volume volume) {
        ModelAndView result;

        result = this.createEditModelAndView2(volume, null);
        return result;
    }
    protected ModelAndView createEditModelAndView2(final Volume volume, final String messageCode) {
        ModelAndView result;

        Collection<NewsPaper> newsPapers= newsPaperService.findAll();

        result = new ModelAndView("volume/addNewsPaper");
        result.addObject("volume", volume);
        result.addObject("newsPapers",newsPapers);
        result.addObject("actionUri","volume/user/addNewsPaper.do");
        result.addObject("message", messageCode);

        return result;
    }
}
