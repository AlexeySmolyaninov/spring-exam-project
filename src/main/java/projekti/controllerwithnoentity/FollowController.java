package projekti.controllerwithnoentity;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.Notification;
import projekti.account.Account;
import projekti.account.AccountRepository;
import projekti.followers.FollowService;
import projekti.followers.FollowingDetail;

/**
 *
 * @author Alexey Smolyaninov
 */

@Controller
public class FollowController {
    
    @Autowired
    private FollowService followService;
    
    @PostMapping("/follow")
    public String startFollowingAccount(@RequestParam Long id, Model model){
        Notification notification = followService.startFollowingAccount(id);
        model.addAttribute("notification", notification);
        return "search";
    }
}
