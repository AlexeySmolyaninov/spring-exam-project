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
    private LikePostService likePostService;
    
    @GetMapping("/posts/{id}/likes")
    @ResponseBody
    public List<LikePost> getPostLikes(@PathVariable Long id){
        return likePostService.getLikeForPost(id);
    }
    
    @PostMapping("/posts/{id}/like")
    @ResponseBody
    public Notification likeAndUnlikeAPost(@PathVariable Long id){
        return likePostService.likeAndUnlikeAPost(id);
    }
}
