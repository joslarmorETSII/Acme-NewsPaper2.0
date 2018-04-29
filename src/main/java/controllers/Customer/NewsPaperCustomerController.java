package controllers.Customer;

import controllers.AbstractController;
import domain.*;
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
import services.VolumeService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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

    @Autowired
    private VolumeService   volumeService;

    // Listing  --------------------------------------------------------------
    //Listado de newspaper donde estoy suscrito
    @RequestMapping(value = "/listNewsPaperCustomer", method = RequestMethod.GET)
    public ModelAndView list() {

        ModelAndView result;
        Customer customer = customerService.findByPrincipal();
        Collection<NewsPaper> newsPapers= customer.getNewsPapers();

        result = new ModelAndView("newsPaper/listNewsPaperCustomer");
        result.addObject("newsPapers", newsPapers);
        result.addObject("customer", customer);
        result.addObject("requestURI", "newsPaper/customer/listNewsPaperCustomer.do");
        return result;
    }
    //Listado de newspaper donte me puedo suscritbir
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView listNotSubscribed() {

        ModelAndView result;
        Customer customer = customerService.findByPrincipal();

        Collection<NewsPaper> newsPapersToSubscribe = newsPaperService.findPublishedAndPrivateNewsPaper();
        newsPapersToSubscribe.removeAll(customer.getNewsPapers());

        result = new ModelAndView("newsPaper/list");
        result.addObject("newsPapers", newsPapersToSubscribe);
        result.addObject("customer", customer);
        result.addObject("requestURI", "newsPaper/customer/list.do");
        return result;
    }
    //Listado de newspaper por volume que son privadas y no privadas
    @RequestMapping(value = "/listNewsPapersPerVolume", method = RequestMethod.GET)
    public ModelAndView listNewsPapersVNP(@RequestParam int volumeId) {
        ModelAndView result;
        boolean volumeContieneNewspaper = false;
        Collection<NewsPaper> newsPapers=null;
        Customer customer = this.customerService.findByPrincipal();
        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());

        newsPapers = this.volumeService.findPublishedNewsPaperPerVolume(volumeId);
        Volume volume = this.volumeService.findOne(volumeId);

        if(volume.getCustomers().contains(customer)){
                volumeContieneNewspaper = true;
        }



        result = new ModelAndView("newsPaper/listNewsPaperPerVolume");
        result.addObject("newsPapers", newsPapers);
        result.addObject("requestUri","newsPaper/listNewsPapersPerVolume.do");
        result.addObject("volumeContieneNewspaper", volumeContieneNewspaper);
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;

    }


    //Subscribirse

    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    public ModelAndView subscribe(@RequestParam  int newsPaperId) {

        final ModelAndView result;
        NewsPaper newsPaper;

        newsPaper = this.newsPaperService.findOne(newsPaperId);
        Assert.notNull(newsPaper);

        SubscribeForm subscribeForm = new SubscribeForm();
        subscribeForm.setNewsPaper(newsPaper);

        result = new ModelAndView("customer/subscribeForm");
        result.addObject("subscribeForm", subscribeForm);

        return result;
    }

    // Save ------------------------------------------------------------------------

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final SubscribeForm subscribeForm, final BindingResult binding) {
        ModelAndView result;
        Customer customer;
        NewsPaper newsPaper;

        try {
            CreditCard creditCard = customerService.reconstructSubscribe(subscribeForm, binding);

            if (binding.hasErrors())
                result = this.createEditModelAndView2(subscribeForm, "general.commit.error");
            else {
                result = new ModelAndView("redirect:list.do");
                customer = customerService.findByPrincipal();
                newsPaper = subscribeForm.getNewsPaper();
                newsPaper.getCustomers().add(customer);
                customer.getNewsPapers().add(newsPaper);
                CreditCard saved=creditCardService.save(creditCard);
                customer.setCreditCard(saved);
                this.customerService.save(customer);
                this.newsPaperService.save(newsPaper);
            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView2(subscribeForm, "general.commit.error");
        }

        return result;
    }

    //Unsuscribe
    @RequestMapping(value = "/unsubscribe", method = RequestMethod.GET)
    public ModelAndView unsuscribe(@RequestParam int newsPaperId){
        ModelAndView result;
        NewsPaper newsPaper= newsPaperService.findOne(newsPaperId);
        try{

            newsPaperService.unsuscribe(newsPaper);
            result = new ModelAndView("redirect:listAllVolumes.do");
        }catch (Throwable oops){
            result = createEditModelAndView(newsPaper,"general.commit.error");
        }
        return result;
    }

    // Ancillary methods

    protected ModelAndView createEditModelAndView(final NewsPaper newsPaper) {
        ModelAndView result;

        result = this.createEditModelAndView(newsPaper, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final NewsPaper newsPaper, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("newsPaper/edit");
        result.addObject("newsPaper", newsPaper);
        result.addObject("message", messageCode);

        return result;
    }

    protected ModelAndView createEditModelAndView2(SubscribeForm subscribeForm, final String messageCode) {
        ModelAndView result;
        Customer customer;
        NewsPaper newsPaper;
        CreditCard creditCard;

        customer = customerService.findByPrincipal();
        creditCard = customer.getCreditCard();
        result = new ModelAndView("customer/subscribeForm");
        result.addObject("subscribeForm", subscribeForm);
        result.addObject("message", messageCode);

        return result;
    }
}
