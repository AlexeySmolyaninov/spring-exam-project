package projekti.controllerwithnoentity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.Notification;
import projekti.account.Account;
import projekti.account.AccountService;
import projekti.followers.FollowService;

/**
 *
 * @author Alexey Smolyaninov
 */

@Controller
public class FollowController {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private FollowService followService;
    
    @PostMapping("/follow")
    public String startFollowingAccount(@RequestParam Long id, Model model){
        Notification notification = followService.startFollowingAccount(id);
        model.addAttribute("notification", notification);
        return "search";
    }
    
    @DeleteMapping("/unfollow")
    public String unfollowAccount(@RequestParam Long id, Model model){
        Notification notification = followService.unfollowAccount(id);
        return "redirect:/accounts";
    }
}
