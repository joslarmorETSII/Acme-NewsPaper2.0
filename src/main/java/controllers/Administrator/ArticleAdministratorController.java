package controllers.Administrator;


import controllers.AbstractController;
import domain.Administrator;
import domain.Article;
import domain.NewsPaper;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.AdministratorService;
import services.ArticleService;
import services.NewsPaperService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/article/administrator")
public class ArticleAdministratorController extends AbstractController {
    // Services --------------------------------------------

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AdministratorService administratorService;


    // Constructor --------------------------------------------

    public ArticleAdministratorController() {
        super();
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        User user;
        Collection<Article> articlesTaboo = articleService.articleTaboo();

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());

        Administrator administrator= administratorService.findByPrincipal();


        result = new ModelAndView("article/list");
        result.addObject("articles", articlesTaboo);
        result.addObject("Administrator",administrator);
        result.addObject("requestUri","article/administrator/list.do");

        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;

    }
}
