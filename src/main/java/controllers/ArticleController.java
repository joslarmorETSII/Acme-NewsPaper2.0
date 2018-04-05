package controllers;

import domain.Article;
import domain.NewsPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ArticleService;
import services.NewsPaperService;

@Controller
@RequestMapping("/article")
public class ArticleController {

    // Services --------------------------------------------

    @Autowired
    private ArticleService articleService;

    // Constructors -----------------------------------------------------------

    public ArticleController(){
        super();
    }

    // Display ----------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int articleId) {
        ModelAndView result;
        Article article;

        article = this.articleService.findOne(articleId);
        result = new ModelAndView("article/display");
        result.addObject("article", article);

        return result;
    }


}
