package projekti.controllerwithnoentity;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.account.Account;
import projekti.account.AccountService;

/**
 *
 * @author Alexey Smolyaninov
 */
@Controller
public class SearchController {
    
    @Autowired
    private AccountService accountService;
    
    @GetMapping("/search")
    public String searchUsersByName(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            Model model){
        if(firstname != null && lastname != null ){
            List<Account> accounts; 
            if(firstname.isEmpty() && lastname.isEmpty()){
                accounts = accountService.listAccounts();
            } else {
               accounts = accountService.searchAccounts(firstname, lastname); 
            }
            model.addAttribute("searchResult", accounts);
        }
        
        return "search";
    }
}
