/*
package controllers;

import domain.CreditCard;
import domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.CreditCardService;
import services.CustomerService;

import javax.validation.Valid;

@Controller
@RequestMapping("/creditCard")
public class CreditCardController extends AbstractController{

    @Autowired
    private CreditCardService creditCardService;


    @Autowired
    private CustomerService customerService;


    public CreditCardController() {
        super();
    }

    // Create

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {

        ModelAndView res;
        CreditCard creditCard;

        creditCard = this.creditCardService.create();

        res = this.createEditModelAndView(creditCard);

        return res;
    }

    //Edit

    @RequestMapping("/edit")
    public ModelAndView edit() {

        ModelAndView res;
        Customer customer;
        CreditCard creditCard;


        customer = customerService.findByPrincipal();
        creditCard = customer.getCreditCard();
        if(creditCard==null)
            creditCard=creditCardService.create();
        res = new ModelAndView("creditCard/edit");
        res.addObject("creditCard", creditCard);


        return res;
    }

    //Save

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final CreditCard creditCard, final BindingResult binding) {

        ModelAndView res;
        Customer customer;
        CreditCard creditCard1;

        if (binding.hasErrors())
            res = this.createEditModelAndView(creditCard);
        else
            try {

                customer = customerService.findByPrincipal();
                creditCard1 = customer.getCreditCard();
                if(creditCard1!=null)
                    creditCardService.delete(creditCard1);
                customer.setCreditCard(creditCardService.save(creditCard));
                customerService.save(customer);

                res = new ModelAndView("redirect:/welcome/index.do");

            } catch (final Throwable oops) {
                res = this.createEditModelAndView(creditCard, "creditCard.commit.error");
            }
        return res;
    }

    // Delete

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(CreditCard creditCard) {

        ModelAndView res;

        try {
            this.creditCardService.delete(creditCard);
            res = new ModelAndView("redirect:/welcome/index.do");
        } catch (final Throwable oops) {
            res = this.createEditModelAndView(creditCard, "creditCard.commit.error");
        }
        return res;
    }

    // Ancillary methods

    protected ModelAndView createEditModelAndView(final CreditCard creditCard) {

        ModelAndView res;

        res = this.createEditModelAndView(creditCard, null);
        return res;
    }

    protected ModelAndView createEditModelAndView(final CreditCard creditCard, final String message) {

        ModelAndView res;

        res = new ModelAndView("creditCard/edit");

        res.addObject("creditCard", creditCard);
        res.addObject("message", message);

        return res;
    }
}
*/
