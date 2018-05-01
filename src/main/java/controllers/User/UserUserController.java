package controllers.User;

import controllers.AbstractController;
import domain.Article;
import domain.User;
import forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/user/user")
public class UserUserController extends AbstractController{

    // Services --------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    // Constructor -----------------------------------------
    public UserUserController() {
        super();
    }

    // Edition Profile ----------------------------------------------------------------

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public ModelAndView editProfile() {

        ModelAndView result;

        final User user = this.userService.findByPrincipal();
        result = this.createEditModelAndView(user);

        return result;
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final User user, final BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors())
            result = this.createEditModelAndView(user);
        else
            try {
                this.userService.save(user);
                result = new ModelAndView("redirect:/welcome/index.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(user, "user.commit.error");
            }
        return result;
    }



    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam Integer userId) {
        ModelAndView result;
        User user;
        User principal;
        Collection<Article> articles;

        user = userService.findOne(userId);
        principal = userService.findByPrincipal();
        articles=articleService.findPublishArticlesByUserId(userId);
        result = new ModelAndView("user/display");
        result.addObject("user", user);
        result.addObject("principal",principal);
        result.addObject("articles", articles);
        result.addObject("requestURI","user/display.do");
        result.addObject("chirps",user.getChirps());
        result.addObject("esSeguido", principal.getFollowings().contains(user));

        return result;
    }





    @RequestMapping(value = "/list-followers", method = RequestMethod.GET)
    public ModelAndView listFollowers() {
        ModelAndView result;
        User principal;

        principal = userService.findByPrincipal();
        result = new ModelAndView("user/followers");
        result.addObject("followers", principal.getFollowers());

        result.addObject("requestURI", "list-followers.do");
        return result;

    }

    @RequestMapping(value = "/list-following", method = RequestMethod.GET)
    public ModelAndView listFollowing() {
        ModelAndView result;
        User principal;

        principal = userService.findByPrincipal();
        result = new ModelAndView("user/following");
        result.addObject("users", principal.getFollowings());

        result.addObject("requestURI", "user/user/list-following.do");
        return result;

    }

    //  Follow - Unfollow -----------------------------------------------------------

    @RequestMapping(value = "/follow", method = RequestMethod.GET)
    public ModelAndView doFollow(@RequestParam Integer userId) {
        ModelAndView result;

        result =listFollowing();
        try {
            userService.follow(userId);
        }catch (Throwable oops){
            return new ModelAndView("misc/panic");
        }

        return result;
    }


    @RequestMapping(value = "/unfollow", method = RequestMethod.GET)
    public ModelAndView doUnfollow(@RequestParam Integer userId) {
        ModelAndView result;

        result = listFollowing();
        try {
            userService.unfollow(userId);
        }catch (Throwable oops){
            return new ModelAndView("misc/panic");
        }

        return result;
    }

    // Ancillary methods ------------------------------------------------------------



    private ModelAndView createEditModelAndView(final User user) {

        return this.createEditModelAndView(user, null);
    }

    private ModelAndView createEditModelAndView(final User user, final String message) {

        final ModelAndView result = new ModelAndView("user/editProfile");

        result.addObject("user", user);
        result.addObject("message", message);

        return result;
    }

}

