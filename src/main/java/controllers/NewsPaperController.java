package controllers;

import domain.NewsPaper;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.NewsPaperService;
import services.UserService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/newsPaper")
public class NewsPaperController {
    // Services --------------------------------------------

    @Autowired
    private NewsPaperService newsPaperService;




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

        formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        momentEn = formatterEn.format(new Date());
        newsPapers=newsPaperService.findPublishedNewsPaper();


        result = new ModelAndView("newsPaper/list");
        result.addObject("newsPapers", newsPapers);
        result.addObject("requestUri","newsPaper/listAll.do");

        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

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
        result.addObject("cancelURI", "newsPaper/listAll.do");


        return result;
    }
}
