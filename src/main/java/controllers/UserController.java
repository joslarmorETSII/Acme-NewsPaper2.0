package controllers;

import domain.*;
import forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{

    // Services --------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NewsPaperService    newsPaperService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private ActorService actorService;

    // Constructor -----------------------------------------
    public UserController() {
        super();
    }

//Edition --------------------------------------------------------------------

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView edit() {

        ModelAndView result;
        result = new ModelAndView("user/edit");

        result.addObject("userForm", new UserForm());

        return result;
    }

    // Save ------------------------------------------------------------------------

    @RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final UserForm userForm, final BindingResult binding) {
        ModelAndView result;
        User user;

        try {
            user = this.userService.reconstruct(userForm, binding);

            if (binding.hasErrors())
                result = createEditModelAndView(userForm);
            else {
                result = new ModelAndView("redirect:/welcome/index.do");

                user = this.userService.save(user);

            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView(userForm, "user.commit.error");
        }

        return result;
    }

    // Listing -------------------------------------------------------


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView result;

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession", request.getRequestURI());

        result = new ModelAndView("user/list");
        result.addObject("users", userService.findAll());
        result.addObject("requestURI", "user/list.do");
        return result;

    }

    @RequestMapping(value = "/display", method = RequestMethod.GET)
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
        result.addObject("requestURI", "user/display.do");

        return result;
    }
    // Search ---------------------------------------------------------------

    @RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
    public ModelAndView action2(@Valid Search search, BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView result;
        Collection<Article> articles;
        Collection<NewsPaper> newsPapers;
        Search searchSystem;

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession", request.getRequestURI());

        if(bindingResult.hasErrors()){
            return createModelAndView(search);
        }else try {

            result = new ModelAndView("article/search");
            articles = articleService.findbyTitleAndBodyAndSummary(search.getKeyword());
            newsPapers = newsPaperService.searchNewspapers(search.getKeyword());
            searchSystem = searchService.getSearch();
            //searchSystem.setKeyword(search.getKeyword());
            searchSystem.setNewsPapers(newsPapers);
            searchSystem.setArticles(articles);
            searchService.save(searchSystem);

            result.addObject("articles", articles);
            result.addObject("newsPapers",newsPapers);
            result.addObject("requestURI", "user/search.do");
        }catch (Throwable oops){
            return createModelAndView(search,"general.commit.error");
        }
        return result;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView action2(HttpServletRequest request) {
        ModelAndView result;

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession", request.getRequestURI());

        result = new ModelAndView("article/search");
        Search search = searchService.getSearch();

        result.addObject("search", search);
        result.addObject("articles", search.getArticles());
        result.addObject("newsPapers",search.getNewsPapers());
        result.addObject("requestURI", "user/search.do");
        result.addObject("cancelURI", "welcome/index.do");

        return result;
    }

    // Search logueado ----------------
    @RequestMapping(value = "/actor/search", method = RequestMethod.POST, params = "search")
    public ModelAndView action3(@Valid Search search, BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView result;
        Collection<Article> articles;
        Collection<NewsPaper> newsPapers;
        Search mySearch;
        Actor principal;

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession", request.getRequestURI());

        if(bindingResult.hasErrors()){
            return createModelAndView(search);
        }else try {
            result = new ModelAndView("article/search");

            principal = actorService.findByPrincipal();
            articles = articleService.findbyTitleAndBodyAndSummary(search.getKeyword());
            newsPapers = newsPaperService.searchNewspapers(search.getKeyword());
            mySearch = principal.getSearch();
            mySearch.setKeyword(search.getKeyword());
            mySearch.setNewsPapers(newsPapers);
            mySearch.setArticles(articles);
            searchService.save(mySearch);

            result.addObject("articles", articles);
            result.addObject("newsPapers",newsPapers);
            result.addObject("requestURI", "user/actor/search.do");
        }catch (Throwable oops){
            return createModelAndView(search,"general.commit.error");
        }
        return result;
    }

    @RequestMapping(value = "/actor/search", method = RequestMethod.GET)
    public ModelAndView action4(HttpServletRequest request) {
        ModelAndView result;
        Actor principal;

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession", request.getRequestURI());

        result = new ModelAndView("article/search");
        principal = actorService.findByPrincipal();
        Search search = principal.getSearch();

        result.addObject("search", search);
        result.addObject("articles", search.getArticles());
        result.addObject("newsPapers",search.getNewsPapers());
        result.addObject("requestURI", "user/actor/search.do");
        result.addObject("cancelURI", "welcome/index.do");

        return result;
    }

    // Ancillary methods ------------------------------------------------------------

    private ModelAndView createEditModelAndView( UserForm userForm) {
        return createEditModelAndView(userForm,null);
    }

        private ModelAndView createEditModelAndView( UserForm userForm,  String message) {

        ModelAndView result = new ModelAndView("user/edit");

        result.addObject("userForm", userForm);
        result.addObject("message", message);
        return result;
    }

    protected ModelAndView createModelAndView(Search search) {
        return createModelAndView(search,null);
    }

    protected ModelAndView createModelAndView(Search search, String messageCode){
        ModelAndView result;
        result =new ModelAndView("article/search");
        result.addObject("message",messageCode);
        result.addObject("search",search);
        return result;
    }
}