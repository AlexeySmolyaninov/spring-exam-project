package projekti.controllerwithnoentity;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public String searchTemplate(){
        return "search";
    }
    
    @GetMapping("/findAccounts")
    @ResponseBody()
    public List<Account> searchUsersByName(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            Model model){
        List<Account> accounts = new ArrayList<>();
        if(firstname != null && lastname != null ){
            if(firstname.isEmpty() && lastname.isEmpty()){
                accounts = accountService.listAccounts();
            } else {
               accounts = accountService.searchAccounts(firstname, lastname); 
            }
        }
        
        return accounts;
    }
}
