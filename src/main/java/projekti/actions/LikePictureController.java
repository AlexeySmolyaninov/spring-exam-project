package projekti.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import projekti.Notification;

/**
 *
 * @author Alexey Smolyaninov
 */

@Controller
public class LikePictureController {
    
    @Autowired
    private LikePictureService likePictureService;
    
    @PostMapping("pictures/{id}/like")
    @ResponseBody
    public Notification likeOrUnlikePicture(@PathVariable Long id){
        return likePictureService.likeOrUnlikeAPicture(id);
    }
}
