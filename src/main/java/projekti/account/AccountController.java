package projekti.account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import projekti.Notification;
import projekti.followers.FollowingDetail;
import projekti.post.Post;
import projekti.post.PostService;

/**
 *
 * @author Alexey Smolyaninov
 */

@Controller
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private PostService postService;
    
    @PostMapping("/accounts")
    public String createAccount(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String profilename,
            Model model){
        Notification notification = accountService.createAccount(
                username,
                password,
                firstname,
                lastname,
                profilename);
        model.addAttribute("notification", notification);
        return "registration";
    }
    
    @GetMapping("/accounts")
    public String redirectToProfile(Authentication auth){
        return "redirect:/accounts/" + accountService.findAccountByUsername(auth.getName()).getProfileName();
    }
    
    @GetMapping("/accounts/{profilename}")
    public String userProfile(@PathVariable String profilename, Model model, Authentication auth){
        Account account = accountService.findAccountByProfileName(profilename);
        String logedUserProfileName = accountService.findAccountByUsername(auth.getName()).getProfileName();
        Long picId = account.getProfilePicture() != null ? account.getProfilePicture().getId() : null;
        List<FollowingDetail> followingPeople = account.getFollowingPeople() != null ? account.getFollowingPeople(): new ArrayList<>();
        List<Post> posts = postService.getOwnAndFolloweePosts(profilename);
        model.addAttribute("firstname", account.getFirstName());
        model.addAttribute("lastname", account.getLastName());
        model.addAttribute("profilename", account.getProfileName());
        model.addAttribute("logedUserProfileName", logedUserProfileName);
        model.addAttribute("followingPeople", followingPeople);
        model.addAttribute("posts", posts);
        model.addAttribute("profilePicId", picId);
        return "user-home";
    }
    
    @GetMapping("/myDetails")
    @ResponseBody
    public Account getMyDetails(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account myDetails = accountService.findAccountByUsername(auth.getName());
        return myDetails;
    }
    
    @GetMapping("/accounts/{profilename}/followings")
    @ResponseBody
    public List<Account> getFollwing(@PathVariable String profilename){
        Account account = accountService.findAccountByProfileName(profilename);
        return account.getFollowingPeople().stream()
                .map(following -> following.getFollowee())
                .collect(Collectors.toList());
    }
    
}
