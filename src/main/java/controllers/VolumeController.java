/*
package controllers;
import domain.NewsPaper;
import domain.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.VolumeService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/volume")
public class VolumeController extends AbstractController {

    //Services -----------------------------------------------------

    @Autowired
    private VolumeService volumeService;

    // Constructor --------------------------------------------

    public VolumeController() {
        super();
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;

        Collection<Volume> volumes = null;

        volumes=this.volumeService.findAll();

        result = new ModelAndView("volume/list");
        result.addObject("volumes", volumes);
        result.addObject("requestURI","volume/list.do");
        result.addObject("cancelUri", "volume/list.do");

        return result;

    }
}
*/
