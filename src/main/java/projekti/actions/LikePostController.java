/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.actions;

import com.google.common.base.Objects;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import projekti.Notification;
import projekti.account.Account;
import projekti.account.AccountRepository;
import projekti.post.Post;
import projekti.post.PostRepository;

/**
 *
 * @author Alexey
 */
@Controller
public class LikePostController {
    
    @Autowired
    private LikePostRepository likePostRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @GetMapping("/posts/{id}/likes")
    @ResponseBody
    public List<LikePost> getPostLikes(@PathVariable Long id){
        Post post = postRepository.getOne(id);
        return likePostRepository.findAllByPost(post);
    }
    
    @PostMapping("/posts/{id}/like")
    @ResponseBody
    public Notification likeAndUnlikeAPost(@PathVariable Long id, Authentication auth){
        Post post = postRepository.getOne(id);
        Account owner = accountRepository.findByUsername(auth.getName());
        List<LikePost> likesForPost = likePostRepository.findAllByPost(post);
        LikePost like = likesForPost.stream()
                .filter(likeStream -> Objects.equal(likeStream.getOwner().getProfileName(), owner.getProfileName()))
                .findFirst()
                .orElse(null);
        if(like == null) {
            likePostRepository.save(new LikePost(owner, post, owner.getId()));
            return new Notification(true, "You have liked the post!");
        } else {
            likePostRepository.delete(like);
            return new Notification(false, "You have remove your like"); //value set to false to be able to destinguish what has been performed like or unlike
        }
        
    }
}
