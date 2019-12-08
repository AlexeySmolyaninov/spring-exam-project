package projekti.account;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.Notification;
import projekti.followers.FollowingDetail;

/**
 *
 * @author Alexey Smolyaninov
 */

@Controller
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
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
    public String userProfile(@PathVariable String profilename, Model model){
        Account account = accountService.findAccountByProfileName(profilename);
        List<FollowingDetail> followingList = accountService.fetchFollowingList(profilename);
        model.addAttribute("firstname", account.getFirstName());
        model.addAttribute("lastname", account.getLastName());
        model.addAttribute("followingList", followingList);
        return "user-home";
    }
    
}
