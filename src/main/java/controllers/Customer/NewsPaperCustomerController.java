package controllers.Customer;

import controllers.AbstractController;
import domain.Customer;
import domain.NewsPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CustomerService;
import services.NewsPaperService;

import java.util.Collection;

@Controller
@RequestMapping("/customer/newsPaper")
public class NewsPaperCustomerController extends AbstractController{

    // Services ------------------------------------------------------------

    @Autowired
    private CustomerService customerService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Listing  --------------------------------------------------------------

//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public ModelAndView list() {
//
//        ModelAndView result;
//
//        Collection<NewsPaper> newsPapers= customerService.findByPrincipal().getNewsPapers();
//        Collection<NewsPaper> newsPapersPrivate= newsPaperService.findNewsPapersPrivate(customerService.findByPrincipal().getId());
//
//        result = new ModelAndView("customer/list");
//        result.addObject("newsPapers", newsPapers);
//        result.addObject("newsPapersPrivate", newsPapersPrivate);
//
//        result.addObject("requestURI", "customer/newsPaper/list.do");
//        return result;
//
//    }


}
