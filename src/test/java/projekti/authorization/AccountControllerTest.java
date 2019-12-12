package projekti.authorization;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import projekti.account.AccountRepository;

//mockMvc get- and poost methods
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import projekti.account.Account;

/**
 *
 * @author Alexey Smolyaninov
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Test
    public void registerAccount() throws Exception{
        StringBuilder uri = new StringBuilder("/accounts?");
        uri.append("username=");
        uri.append("alex");
        uri.append("&password=");
        uri.append("alex");
        uri.append("&firstname=");
        uri.append("aleksi");
        uri.append("&lastname=");
        uri.append("smol");
        uri.append("&profilename=");
        uri.append("aksu");
        mockMvc.perform(post(uri.toString()));
        Account aksu = accountRepository.findByProfileName("aksu");
        assertTrue(aksu.getUsername().equals("alex"));
    }
    
    @After
    public void deleteAllUsers(){
        accountRepository.deleteAll();
    }
    
}
