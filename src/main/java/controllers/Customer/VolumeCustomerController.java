package controllers.Customer;

import controllers.AbstractController;
import domain.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.CustomerService;
import services.VolumeService;

import java.util.Collection;

@Controller
@RequestMapping("/volume/customer")
public class VolumeCustomerController extends AbstractController{

    //Services --------------------------------------------------------

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VolumeService volumeService;

    // Listing  --------------------------------------------------------------

    @RequestMapping(value = "/listVolumeCustomer", method = RequestMethod.GET)
    public ModelAndView list() {

        ModelAndView result;

        Collection<Volume> volumes= customerService.findByPrincipal().getVolumes();

        result = new ModelAndView("volume/listVolumeCustomer");
        result.addObject("volumes", volumes);

        result.addObject("requestURI", "volume/customer/listVolumeCustomer.do");
        return result;
    }
}
