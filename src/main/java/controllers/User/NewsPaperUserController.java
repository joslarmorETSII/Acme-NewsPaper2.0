package controllers.User;

import controllers.AbstractController;
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
import services.NewsPaperService;
import services.UserService;
import services.VolumeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Controller
@RequestMapping("/newsPaper/user")
public class NewsPaperUserController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private UserService userService;

    @Autowired
    private VolumeService volumeService;

    // Constructor --------------------------------------------

    public NewsPaperUserController() {
        super();
    }

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        NewsPaper newsPaper= null ;

        newsPaper = this.newsPaperService.create();
        result = this.createEditModelAndView(newsPaper);

        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        User user;
        Collection<NewsPaper> newsPapers=null;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());

        user = userService.findByPrincipal();
        newsPapers=user.getNewsPapers();

        result = new ModelAndView("newsPaper/list");
        result.addObject("newsPapers", newsPapers);
        result.addObject("user",user);
        result.addObject("requestUri","newsPaper/user/list.do");

        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;

    }

    @RequestMapping(value = "/listNewspaperUserVolume", method = RequestMethod.GET)
    public ModelAndView listNewspaperUserVolume(@RequestParam int volumeId) {
        ModelAndView result;
        User user;
        Collection<NewsPaper> newsPapers=null;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());
        user = this.userService.findByPrincipal();

        Volume volume =this.volumeService.findOne(volumeId);
        newsPapers = volume.getNewsPapers();

        result = new ModelAndView("newsPaper/listNewsPaperPerVolume");
        result.addObject("newsPapers", newsPapers);
        result.addObject("requestUri","newsPaper/user/listNewspaperUserVolume.do");
        result.addObject("user",user);
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;

    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam  int newsPaperId) {
        final ModelAndView result;
        NewsPaper newsPaper;
        newsPaper = this.newsPaperService.findOneToEdit(newsPaperId);
        Assert.notNull(newsPaper);
        result = this.createEditModelAndView(newsPaper);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(NewsPaper newsPaperPruned, final BindingResult binding) {
        ModelAndView result;



            try {
                NewsPaper newsPaper = this.newsPaperService.reconstructS(newsPaperPruned,binding);

                if (binding.hasErrors()){
                    result = this.createEditModelAndView(newsPaperPruned);
                }
                else {
                    this.newsPaperService.save(newsPaper);
                    result = new ModelAndView("redirect:list.do");
                }
            } catch (final Throwable oops) {
                if (binding.hasErrors()){
                    result = this.createEditModelAndView(newsPaperPruned);
                }else{
                    result = this.createEditModelAndView(newsPaperPruned, "newsPaper.commit.error");
                }
            }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,params = "delete")
    public ModelAndView edit(NewsPaper newsPaperPruned){
        ModelAndView result;

        NewsPaper newsPaper = this.newsPaperService.findOne(newsPaperPruned.getId());
        try{
            newsPaperService.delete(newsPaper);
            result = new ModelAndView("redirect:list.do");
        }catch (Throwable oops){
            result = createEditModelAndView(newsPaper,"newsPaper.commit.error");
        }

        return result;
    }

    @RequestMapping(value = "/publish", method = RequestMethod.GET)
    public ModelAndView publish(@RequestParam  int newsPaperId) {
        final ModelAndView result;
        NewsPaper newsPaper;
        NewsPaper res;
        newsPaper = this.newsPaperService.findOneToEdit(newsPaperId);
        Assert.notNull(newsPaper);
        this.newsPaperService.findOneToPublish(newsPaper);
        result = new ModelAndView("redirect:list.do");
        return result;
    }

    // Display ----------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int newsPaperId) {
        ModelAndView result;
        NewsPaper newsPaper;

        newsPaper = this.newsPaperService.findOne(newsPaperId);
        result = new ModelAndView("newsPaper/display");
        result.addObject("newsPaper", newsPaper);
        result.addObject("cancelURI", "newsPaper/user/list.do");


        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final NewsPaper newsPaper) {
        ModelAndView result;

        result = this.createEditModelAndView(newsPaper, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final NewsPaper newsPaper, final String messageCode) {
        ModelAndView result;
        result = new ModelAndView("newsPaper/edit");
        result.addObject("newsPaper", newsPaper);
        result.addObject("message", messageCode);
        result.addObject("actionUri","newsPaper/user/edit.do");

        return result;
    }
}

