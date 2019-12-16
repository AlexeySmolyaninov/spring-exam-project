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
import projekti.post.Post;
import projekti.post.PostRepository;

/**
 *
 * @author Alexey Smolyaninov
 */

@Service
public class LikePostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private LikePostRepository likePostRepository;
    
    public List<LikePost> getLikeForPost(Long id){
        Post post = postRepository.getOne(id);
        return likePostRepository.findAllByPost(post);
    }
    
    public Notification likeAndUnlikeAPost(Long postId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Post post = postRepository.getOne(postId);
        Account owner = accountRepository.findByUsername(auth.getName());
        List<LikePost> likesForPost = likePostRepository.findAllByPost(post);
        LikePost like = likesForPost.stream()
                .filter(likeStream -> Objects.equal(likeStream.getOwner().getProfileName(), owner.getProfileName()))
                .findFirst()
                .orElse(null);
        if(like == null) {
            likePostRepository.save(new LikePost(owner, post));
            return new Notification(true, "You have liked the post!");
        } else {
            likePostRepository.delete(like);
            return new Notification(false, "You have remove your like"); //value set to false to be able to destinguish what has been performed like or unlike
        }
    }
}
