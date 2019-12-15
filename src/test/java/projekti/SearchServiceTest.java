package projekti;

import java.util.List;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import projekti.account.AccountService;

import projekti.account.Account;
import projekti.account.AccountRepository;

/**
 *
 * @author Alexey Smolyaninov
 */

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SearchServiceTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Before
    public void createUsers(){
        accountService.createAccount("alex", "alex", "Alexander", "Val", "bigal");
        accountService.createAccount("jordan", "jordan", "Michael", "Jordan", "flyman");
        accountService.createAccount("lebron", "lebron", "James", "Lebron", "dunkman");
    }
    
    @Test
    @WithMockUser(username = "alex", password = "alex")
    public void searchAllUsers() throws Exception{
        
        List<Account> accounts = accountService.searchAccounts("", "");
        Assert.assertTrue(accounts.size() == 2);
        Assert.assertTrue(accounts
                .stream()
                .filter(account -> Objects.equals(account.getUsername(), "jordan"))
                .count() == 1
        );
    }
    
    @Test
    @WithMockUser(username = "alex", password = "alex")
    public void searchParticularUser() throws Exception{
        
        List<Account> accounts = accountService.searchAccounts("Michael", "");
        Assert.assertTrue(accounts.size() == 1);
        Assert.assertTrue(accounts
                .stream()
                .filter(account -> Objects.equals(account.getProfileName(), "flyman"))
                .count() == 1
        );
    }
 
}
