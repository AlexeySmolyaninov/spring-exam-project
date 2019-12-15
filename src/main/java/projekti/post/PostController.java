package projekti.post;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Alexey Smolyaninov
 */

@Controller
public class PostController {

    @Autowired
    PostService postService;
    
    @GetMapping("/accounts/{profilename}/posts")
    public List<Post> getOwnAndFolloweePosts(@PathVariable String profilename){
        List<Post> posts = postService.getOwnAndFolloweePosts(profilename);
        return posts;
    }
    
    @PostMapping("/accounts/{profilename}/posts")
    @ResponseBody
    public Post createPost(@RequestBody Post post){
        return postService.createPost(post);
    }
}
