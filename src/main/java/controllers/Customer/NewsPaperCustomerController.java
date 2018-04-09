package controllers.Customer;

import controllers.AbstractController;
import domain.CreditCard;
import domain.Customer;
import domain.NewsPaper;
import forms.SubscribeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import repositories.NewsPaperRepository;
import services.CreditCardService;
import services.CustomerService;
import services.NewsPaperService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/newsPaper/customer")
public class NewsPaperCustomerController extends AbstractController{

    // Services ------------------------------------------------------------

    @Autowired
    private CustomerService customerService;

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private CreditCardService creditCardService;


    // Listing  --------------------------------------------------------------

    @RequestMapping(value = "/listNewsPaperCustomer", method = RequestMethod.GET)
    public ModelAndView list() {

        ModelAndView result;

        Collection<NewsPaper> newsPapers= customerService.findByPrincipal().getNewsPapers();

        result = new ModelAndView("newsPaper/listNewsPaperCustomer");
        result.addObject("newsPapers", newsPapers);

        result.addObject("requestURI", "newsPaper/customer/listNewsPaperCustomer.do");
        return result;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView listNotSubscribed() {

        ModelAndView result;
        Customer customer = customerService.findByPrincipal();

        Collection<NewsPaper> newsPapersToSubscribe = newsPaperService.findPublishedNewsPaper();
        newsPapersToSubscribe.removeAll(customer.getNewsPapers());

        result = new ModelAndView("newsPaper/list");
        result.addObject("newsPapers", newsPapersToSubscribe);
        result.addObject("customer", customer);
        result.addObject("requestURI", "newsPaper/customer/list.do");
        return result;
    }

    //Subscribirse


    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    public ModelAndView subscribe(@RequestParam  int newsPaperId) {

        final ModelAndView result;
        NewsPaper newsPaper;

        newsPaper = this.newsPaperService.findOneToEdit(newsPaperId);
        Assert.notNull(newsPaper);

        SubscribeForm subscribeForm = new SubscribeForm();
        subscribeForm.setNewspaper(newsPaper);

        result = new ModelAndView("customer/subscribeForm.do");
        result.addObject("subscribeForm", subscribeForm);

        return result;
    }

    // Save ------------------------------------------------------------------------

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final SubscribeForm subscribeForm, final BindingResult binding) {
        ModelAndView result;
        Customer customer;
        NewsPaper newsPaper = new NewsPaper();

        try {
            customer = customerService.reconstructSubscribe(subscribeForm, binding);

            if (binding.hasErrors())
                result = this.createEditModelAndView2(subscribeForm, "subscribe.save.error");
            else {
                result = new ModelAndView("redirect:newsPaper/list.do");
                newsPaper.getCustomers().add(customer);
                this.customerService.save(customer);
                this.newsPaperService.save(newsPaper);

            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView2(subscribeForm, "subscribe.save.error");
        }

        return result;
    }

    // Ancillary methods


    protected ModelAndView createEditModelAndView2(SubscribeForm subscribeForm, final String messageCode) {
        ModelAndView result;
        Customer customer;
        NewsPaper newsPaper;
        CreditCard creditCard;

        customer = customerService.findByPrincipal();
        creditCard = customer.getCreditCard();result = new ModelAndView("request/edit");
        result.addObject("subscribeForm", subscribeForm);
        result.addObject("message", messageCode);

        return result;
    }
}
