package projekti.controllerwithnoentity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public Notification startFollowingAccount(@RequestBody Account account){
        return followService.startFollowingAccount(account.getId());
    }
    
    @DeleteMapping("/unfollow")
    @ResponseBody
    public Notification unfollowAccount(@RequestBody Account account){
        return followService.unfollowAccount(account.getId());
    }
}
