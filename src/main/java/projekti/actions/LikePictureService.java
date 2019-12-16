package projekti.actions;

import com.google.common.base.Objects;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projekti.Notification;
import projekti.account.Account;
import projekti.account.AccountRepository;
import projekti.media.Picture;
import projekti.media.PictureRepository;

/**
 *
 * @author Alexey Smolyaninov
 */

@Service
public class LikePictureService {
    
    @Autowired
    private LikePictureRepository likePictureRepository;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    public Notification likeOrUnlikeAPicture(Long picId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Picture picture = pictureRepository.getOne(picId);
        Account owner = accountRepository.findByUsername(auth.getName());
        List<LikePicture> likesForPost = likePictureRepository.findAllByPicture(picture);
        LikePicture like = likesForPost.stream()
                .filter(likeStream -> Objects.equal(likeStream.getOwner().getProfileName(), owner.getProfileName()))
                .findFirst()
                .orElse(null);
        if(like == null) {
            likePictureRepository.save(new LikePicture(owner, picture));
            return new Notification(true, "You have liked the post!");
        } else {
            likePictureRepository.delete(like);
            return new Notification(false, "You have remove your like"); //value set to false to be able to destinguish what has been performed like or unlike
        }
    }
}
