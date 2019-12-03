package projekti.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.Notification;

/**
 *
 * @author Alexey Smolyaninov
 */

@Controller
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    @PostMapping("/account")
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
    
}
