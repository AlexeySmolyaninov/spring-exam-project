package projekti.media;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import projekti.Notification;
import projekti.account.Account;
import projekti.account.AccountService;

/**
 *
 * @author Alexey Smolyaninov
 */

@Controller
public class PictureController {
    
    private static final String MIME_TYPE_JPEG = "image/jpeg";
    private static final String MIME_TYPE_GIF = "image/gif";
    private static final String MIME_TYPE_PNG = "image/png";
    
    @Autowired
    private PictureService pictureService;
    
    @Autowired
    private AccountService accountService;
    
    @PostMapping(path = "/uploadpicture")
    public String uploadPicture(
            @RequestParam("file") MultipartFile file,
            @RequestParam("desc") String desc) throws IOException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account owner = accountService.findAccountByUsername(auth.getName());
        
        if(file.getContentType().equalsIgnoreCase(MIME_TYPE_JPEG) ||
        file.getContentType().equalsIgnoreCase(MIME_TYPE_GIF) ||
        file.getContentType().equalsIgnoreCase(MIME_TYPE_PNG)){
            
            Notification notification = pictureService.uploadPicture(file.getBytes(), desc, owner);
        }
        
        return "redirect:/accounts/"+ owner.getProfileName() + "/pictures";
    }
    
    
    @GetMapping(path = "/accounts/{profilename}/pictures", produces = {MIME_TYPE_JPEG, MIME_TYPE_GIF, MIME_TYPE_PNG})
    public String getPictures(@PathVariable String profilename, Model model){
        Account account = accountService.findAccountByProfileName(profilename);
        List<Picture> pictures = pictureService.getPictures(account);
        model.addAttribute("account", account);
        model.addAttribute("pictures", pictures);
        return "album";
    }
    
    @GetMapping("/accounts/{profilename}/pictures/{id}/content")
    @ResponseBody
    public byte[] getPicture(@PathVariable String profilename, @PathVariable Long id){
        return pictureService.getPictureContent(id);
    } 
    
    @DeleteMapping("/accounts/{profilename}/pictures")
    @ResponseBody
    public Picture deletePicture(@RequestBody Picture picture, @PathVariable String profilename){
        return pictureService.deletePicture(picture.getId());
    }
    
    @PostMapping("/accounts/{profilename}/setprofilepicture")
    @ResponseBody
    public Notification setProfilePicture(@RequestBody Picture picture, @PathVariable String profilename){
        return pictureService.setProfilePicture(picture.getId());
    }
}
