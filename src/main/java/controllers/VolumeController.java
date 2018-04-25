package controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import services.VolumeService;

@Controller
@RequestMapping("/customer")
public class VolumeController extends AbstractController {

    //Services -----------------------------------------------------

    @Autowired
    private VolumeService volumeService;
}
