package projekti.authorization;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import projekti.Notification;
import projekti.account.Account;
import projekti.account.AccountRepository;
import projekti.account.AccountService;

/**
 *
 * @author Alexey Smolyaninov
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountService accountService;
    
    @Test
    public void createValidUser(){
        Notification notification = accountService.createAccount(
                "alex",
                "alex",
                "allu",
                "lomanen",
                "aksu");
        assertTrue(notification.isGoodMsg());
        Account accountByUserName = accountRepository.findByUsername("alex");
        Account accountByProfileName = accountRepository.findByProfileName("aksu");
        assertTrue(accountByUserName != null && accountByProfileName != null);
        
    }
    
    @Test
    public void createUserWithoutPw() {
        Notification notification = accountService.createAccount(
                "test", null, null, null, "test");
        assertFalse(notification.isGoodMsg());
        Account accountByUserName = accountRepository.findByUsername("test");
        Account accountByProfileName = accountRepository.findByProfileName("test");
        assertTrue(accountByUserName == null && accountByProfileName == null);
    }
    
    @After
    public void deleteAllUsers(){
        accountRepository.deleteAll();
    }
    
}
