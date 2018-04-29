package controllers;

import domain.Article;
import domain.NewsPaper;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ArticleService;
import services.NewsPaperService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController{

    // Services --------------------------------------------

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Constructors -----------------------------------------------------------

    public ArticleController(){
        super();
    }

    // Display ----------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int articleId) {
        ModelAndView result;
        Article article;
        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        article = this.articleService.findOne(articleId);
        Assert.notNull(article);

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(article.getMoment());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(article.getMoment());


        result = new ModelAndView("article/display");
        result.addObject("article", article);
        result.addObject("advertisement",newsPaperService.selectRandomAdd(article));
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        User user;
        Collection<Article> articles=null;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;


        formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        momentEn = formatterEn.format(new Date());

        //TODO: Solo mostrar los que estan en final mode

        articles=this.articleService.findPublishArticles();

        result = new ModelAndView("article/list");
        result.addObject("articles", articles);
        result.addObject("requestURI","article/listAll.do");
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;

    }


}
