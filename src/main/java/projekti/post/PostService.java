package projekti.post;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projekti.account.Account;
import projekti.account.AccountService;

/**
 *
 * @author Alexey Smolyaninov
 */

@Service
public class PostService {
    
    @Autowired
    PostRepository postRepository;
    
    @Autowired
    AccountService accountService;
    
    public List<Post> getOwnAndFolloweePosts(String profileName){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountService.findAccountByProfileName(profileName);
        List<Post> ownPosts = postRepository
                .findAllByOwner(account, new PageRequest(0, 25, Sort.by(Sort.Direction.DESC, "created")))
                .getContent();
        List<Post> allPosts = new ArrayList<>(ownPosts);
        if(Objects.equals(auth.getName(), account.getUsername())){
            List<Post> followeesPosts = new ArrayList<>();
            account.getFollowingPeople()
                    .stream()
                    .forEach(followeeDetail -> {
                        List<Post> followeePosts = postRepository
                                .findAllByOwner(followeeDetail.getFollowee(), new PageRequest(0, 25, Sort.by(Sort.Direction.DESC, "created")))
                                .getContent();
                        if(followeePosts.size() > 0){
                            followeesPosts.addAll(followeePosts);
                        }
                    });
            
            allPosts.addAll(followeesPosts);
        }
        
        
        return allPosts.stream()
                .sorted((Post o1, Post o2) -> {
                    return o1.getCreated().isBefore(o2.getCreated()) ? 1 :
                            o1.getCreated().isAfter(o2.getCreated()) ? -1 : 0;
                })
                .limit(25)
                .collect(Collectors.toList());
    } 
    
    public Post createPost(Post post){
        if(post.getText() != null && !post.getText().isEmpty()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Account owner = accountService.findAccountByUsername(auth.getName());
            post.setCreated(LocalDateTime.now(ZoneId.of("Europe/Helsinki")));
            post.setOwner(owner); 
            return postRepository.save(post);
        }

        return null;
    }
}
