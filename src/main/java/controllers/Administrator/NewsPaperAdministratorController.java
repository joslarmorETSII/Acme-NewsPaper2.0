package controllers.Administrator;


import controllers.AbstractController;
import domain.Administrator;
import domain.NewsPaper;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.AdministratorService;
import services.NewsPaperService;
import services.UserService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/newsPaper/administrator")
public class NewsPaperAdministratorController extends AbstractController {
    // Services --------------------------------------------

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private AdministratorService administratorService;


    // Constructor --------------------------------------------

    public NewsPaperAdministratorController() {
        super();
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        User user;
        Collection<NewsPaper> newsPapersTaboo = newsPaperService.newsPapersTaboo();

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());

        Administrator administrator= administratorService.findByPrincipal();


        result = new ModelAndView("newsPaper/list");
        result.addObject("newsPapers", newsPapersTaboo);
        result.addObject("Administrator",administrator);
        result.addObject("requestUri","newsPaper/administrator/list.do");

        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;

    }
}
