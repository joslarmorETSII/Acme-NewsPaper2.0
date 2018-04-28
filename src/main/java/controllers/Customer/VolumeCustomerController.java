package controllers.Customer;

import controllers.AbstractController;
import domain.CreditCard;
import domain.Customer;
import domain.NewsPaper;
import domain.Volume;
import forms.SubscribeVolumeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CreditCardService;
import services.CustomerService;
import services.VolumeService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/volume/customer")
public class VolumeCustomerController extends AbstractController{

    //Services --------------------------------------------------------

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private CreditCardService creditCardService;

    // Listing  --------------------------------------------------------------

    @RequestMapping(value = "/listVolumeCustomer", method = RequestMethod.GET)
    public ModelAndView list() {

        ModelAndView result;

        Collection<Volume> volumes= customerService.findByPrincipal().getVolumes();

        result = new ModelAndView("volume/listVolumeCustomer");
        result.addObject("volumes", volumes);
        result.addObject("customer",customerService.findByPrincipal());
        result.addObject("requestURI", "volume/customer/listVolumeCustomer.do");
        return result;
    }

    @RequestMapping(value = "/listAllVolumes", method = RequestMethod.GET)
    public ModelAndView listAllVolumes() {

        ModelAndView result;
        Customer customer = customerService.findByPrincipal();

        Collection<Volume> volumes= volumeService.findAll();
        volumes.removeAll(customer.getVolumes());

        result = new ModelAndView("volume/listVolumeCustomer");
        result.addObject("volumes", volumes);
        result.addObject("customer",customer);
        result.addObject("requestURI", "volume/customer/listAllVolumes.do");
        return result;
    }


    // Subscribe --------------------------------------------------------------

    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    public ModelAndView subscribe(@RequestParam int volumeId) {

        ModelAndView result;
        Volume volume;

        volume = volumeService.findOne(volumeId);
        Assert.notNull(volume);

        SubscribeVolumeForm subscribeVolumeForm = new SubscribeVolumeForm();
        subscribeVolumeForm.setVolume(volume);

        result = new ModelAndView("customer/subscribeVolumeForm");
        result.addObject("subscribeVolumeForm", subscribeVolumeForm);

        return result;
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.GET)
    public ModelAndView unsubscribe(@RequestParam int volumeId) {
        ModelAndView result;
        Volume volume;
        Customer principal;
        Collection<NewsPaper> privateNewsPapers;


        volume = volumeService.findOne(volumeId);
        Assert.notNull(volume);
        principal = customerService.findByPrincipal();
        principal.getVolumes().remove(volume);
        volume.getCustomers().remove(principal);
        privateNewsPapers = volumeService.findPrivateNewsPapersByVolume(volume.getId());
        if(privateNewsPapers.size()>0){
            principal.getNewsPapers().removeAll(privateNewsPapers);
        }
        customerService.save(principal);
        volumeService.save(volume);

        result = new ModelAndView("redirect: listAllVolumes.do");

        return result;
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid SubscribeVolumeForm subscribeVolumeForm, BindingResult binding) {
        ModelAndView result;
        Customer customer;
        Volume volume;
        Collection<NewsPaper> privateNewsPapers;

        try {
            CreditCard creditCard = customerService.reconstructSubscribeVolume(subscribeVolumeForm, binding);

            if (binding.hasErrors())
                result = createEditModelAndView(subscribeVolumeForm);
            else {
                result = new ModelAndView("redirect: listVolumeCustomer.do");
                customer = customerService.findByPrincipal();
                volume = subscribeVolumeForm.getVolume();
                volume.getCustomers().add(customer);
                customer.getVolumes().add(volume);
                CreditCard saved = creditCardService.save(creditCard);
                customer.setCreditCard(saved);
                privateNewsPapers = volumeService.findPrivateNewsPapersByVolume(volume.getId());
                if(privateNewsPapers.size()>0){
                    customer.getNewsPapers().addAll(privateNewsPapers);
                }
                customerService.save(customer);
                volumeService.save(volume);
            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView(subscribeVolumeForm, "general.commit.error");
        }

        return result;
    }

    protected ModelAndView createEditModelAndView(SubscribeVolumeForm subscribeVolumeForm) {
        return createEditModelAndView(subscribeVolumeForm,null);
    }

        protected ModelAndView createEditModelAndView(SubscribeVolumeForm subscribeVolumeForm, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("customer/subscribeVolumeForm");
        result.addObject("subscribeVolumeForm", subscribeVolumeForm);
        result.addObject("message", messageCode);

        return result;
    }

}
