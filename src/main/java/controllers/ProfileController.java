/*
 * ProfileController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import domain.Article;
import domain.NewsPaper;
import domain.Search;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ArticleService;
import services.NewsPaperService;
import services.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/everyone")
public class ProfileController extends AbstractController {



	// Services --------------------------------------------------------------

	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private NewsPaperService newsPaperService;

	// Display ---------------------------------------------------------------

	@RequestMapping(value = "/profile/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam Integer userId) {
		ModelAndView result;
		User user;
		Collection<Article> articles;

		user = userService.findOne(userId);
		articles = articleService.findPublishArticlesByUserId(userId);
		result = new ModelAndView("user/display");
		result.addObject("user", user);
		result.addObject("articles", articles);
		result.addObject("chirps",user.getChirps());
		result.addObject("requestURI", "everyone/profile/display.do");

		return result;
	}
	// Search ---------------------------------------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView action2(@Valid Search search, BindingResult bindingResult) {
		ModelAndView result;
        Collection<Article> articles;
        Collection<NewsPaper> newsPapers;

        if(bindingResult.hasErrors()){
            return createmodelAndView(search);
        }else try {

            result = new ModelAndView("article/search");
            articles = articleService.findbyTitleAndBodyAndSummary(search.getKeyword());
            newsPapers = newsPaperService.searchNewspapers(search.getKeyword());

            result.addObject("articles", articles);
			result.addObject("newsPapers",newsPapers);
			result.addObject("requestURI", "everyone/search.do");
        }catch (Throwable oops){
            return createmodelAndView(search,"general.error");
        }
		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("article/search");
		Search search = new Search();

		result.addObject("search",search);
		result.addObject("requestURI", "everyone/article/search.do");

		return result;
	}


    // Ancillary methods  ---------------------------------------------------------------


    protected ModelAndView createmodelAndView(Search search) {
	    return createmodelAndView(search,null);
    }

    protected ModelAndView createmodelAndView(Search search, String messageCode){
        ModelAndView result;
        result =new ModelAndView("article/search");
        result.addObject("message",messageCode);
        result.addObject("search",search);
        return result;
    }
}
