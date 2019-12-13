package projekti.account;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.Notification;
import projekti.followers.FollowingDetail;

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
        
        if(firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()){
            return new Notification(false, "Missing first name or last name");
        }
        
        if(password == null || password.isEmpty()){
            return new Notification(false, "Password field can't be empty");
        }
        
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setFirstName(firstName);
        account.setLastName(lastName);;
        account.setProfileName(profileName);

        accountRepository.save(account);
        
        if(accountRepository.findById(account.getId()) == null){
            return new Notification(false, "Unknow ERROR!");
        }
        
        return new Notification(true, "User created successfully");
    }
    
    public Account findAccountByUsername(String username){
        return accountRepository.findByUsername(username);
    }
    
    public Account findAccountByProfileName(String profileName){
        return accountRepository.findByProfileName(profileName);
    }
    
    public List<Account> searchAccounts(String firstName, String lastName){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Account> accounts = accountRepository.findByFirstNameIgnoreCaseContainingAndLastNameIgnoreCaseContaining(firstName, lastName).stream()
                .filter(account -> !account.getUsername().equalsIgnoreCase(auth.getName()))
                .collect(Collectors.toList());
        return accounts;
    }
    
    public List<Account> listAccounts(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Account> accounts = accountRepository.findAll().stream()
                .filter(account -> !account.getUsername().equalsIgnoreCase(auth.getName()))
                .collect(Collectors.toList());
        return accounts;
    }
    
    public List<FollowingDetail> fetchFollowingList(String profilename){
        Account account = accountRepository.findByProfileName(profilename);
        return account.getFollowingPeople();
    }
   
}
