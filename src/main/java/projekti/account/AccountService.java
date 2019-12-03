package projekti.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.Notification;

/**
 *
 * @author Alexey Smolyaninov
 */

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Notification createAccount(
        String username,
        String password,
        String firstName,
        String lastName,
        String profileName){
        
        if(accountRepository.findByUsername(username) != null){
            return new Notification(false, "Username " + username + " is already in use");
        }
        
        if(accountRepository.findByProfileName(profileName) != null){
            return new Notification(false, "Profile name " + profileName + " is already in use");
        }
        
        if(password == null || password.isEmpty()){
            return new Notification(false, "Password field can't be empty");
        }
        
        Account account = new Account(
                username, 
                passwordEncoder.encode(password), 
                firstName, 
                lastName, 
                profileName);
        accountRepository.save(account);
        
        if(accountRepository.findById(account.getId()) == null){
            return new Notification(false, "Unknow ERROR!");
        }
        
        return new Notification(true, "User created successfully");
    }
}
