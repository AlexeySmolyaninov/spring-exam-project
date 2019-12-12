package projekti.media;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Alexey Smolyaninov
 */

@Controller
public class PictureController {
    
    @GetMapping("account/{profilename}/pictures")
    public String getPicture(){
        return "gallery";
    }
}
