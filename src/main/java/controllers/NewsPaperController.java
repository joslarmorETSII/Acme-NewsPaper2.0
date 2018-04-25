package controllers;

import domain.NewsPaper;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.NewsPaperService;
import services.UserService;
import services.VolumeService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/newsPaper")
public class NewsPaperController {

    // Services --------------------------------------------

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private ActorService actorService;

    // Constructor --------------------------------------------

    public NewsPaperController() {
        super();
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;

        Collection<NewsPaper> newsPapers=null;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());
        newsPapers=newsPaperService.findPublishedNewsPaper();


        result = new ModelAndView("newsPaper/list");
        result.addObject("newsPapers", newsPapers);
        result.addObject("requestUri","newsPaper/listAll.do");

        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;

    }

    @RequestMapping(value = "/listNewsPapersV", method = RequestMethod.GET)
    public ModelAndView listNewsPapersV(@RequestParam int volumeId) {
        ModelAndView result;

        Collection<NewsPaper> newsPapers=null;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());

        newsPapers = this.volumeService.findPublishedNewsPaperPerVolume(volumeId);

        result = new ModelAndView("newsPaper/list");
        result.addObject("newsPapers", newsPapers);
        result.addObject("requestUri","newsPaper/listNewsPapersV.do");
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;

    }

    @RequestMapping(value = "/listNewsPapersVNP", method = RequestMethod.GET)
    public ModelAndView listNewsPapersVNP(@RequestParam int volumeId) {
        ModelAndView result;

        Collection<NewsPaper> newsPapers=null;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());

        newsPapers = this.volumeService.findPublishedAndPrivateNewsPaperPerVolume(volumeId);



        result = new ModelAndView("newsPaper/list");
        result.addObject("newsPapers", newsPapers);
        result.addObject("requestUri","newsPaper/listNewsPapersVNP.do");

        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;

    }

    // Display ----------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam int newsPaperId) {
        ModelAndView result;
        NewsPaper newsPaper;

        newsPaper = this.newsPaperService.findOne(newsPaperId);
        result = new ModelAndView("newsPaper/display");
        result.addObject("newsPaper", newsPaper);
        result.addObject("cancelURI", "newsPaper/listAll.do");


        return result;
    }
}
