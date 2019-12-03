package projekti.loginregistration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Alexey Smolyaninov
 */

@Controller
public class LoginAndRegistrationController {
    
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
}
