package projekti.media;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projekti.Notification;
import projekti.account.Account;
import projekti.account.AccountRepository;
import projekti.account.AccountService;

/**
 *
 * @author Alexey Smolyaninov
 */

@Service
public class PictureService {
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AccountRepository accountRepository;
    
    public Notification uploadPicture(byte[] content, String desc, Account owner){
        List<Picture> pictures = pictureRepository.findAll();
        if(pictures.size() < 10){
            Picture newPicture = new Picture(content, desc, owner, new ArrayList<>());
            pictureRepository.save(newPicture);
            return new Notification(true, "Picture has been successfully saved!"); 
        }
        return new Notification(false, "You can upload only max of 10 pictures.");
    }
    
    public List<Picture> getPictures(Account account){
        return pictureRepository.findAllByOwner(account);
    }
    
    public byte[] getPictureContent(Long id){
        return pictureRepository.getOne(id).getContent();
    }
    
    public Picture deletePicture(Long id){
        Picture picture = pictureRepository.getOne(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account owner = accountRepository.findByUsername(auth.getName());
        if(picture.getOwner().getUsername().equals(auth.getName()) && !Objects.equals(picture.getId(), owner.getProfilePicture().getId())){
            pictureRepository.delete(picture);
            return picture;
        }
        
        return null;
    }
    
    public Notification setProfilePicture(Long id){
        Picture profilePic = pictureRepository.getOne(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(profilePic.getOwner().getUsername().equals(auth.getName())){
            Account account = accountService.findAccountByUsername(auth.getName());
            account.setProfilePicture(profilePic);
            accountRepository.save(account);
            return new Notification(true, "New profile picture has been set!");
        }
        return new Notification(false, "You are not owning rights of that picture");
    }
}
