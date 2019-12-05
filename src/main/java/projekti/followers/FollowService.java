package projekti.followers;

import java.time.LocalDate;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projekti.Notification;
import projekti.account.Account;
import projekti.account.AccountRepository;

/**
 *
 * @author Alexey Smolyaninov
 */
@Service
public class FollowService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private FollowingDetailRepository followingDetailRepository;
    
    
    public Notification startFollowingAccount(Long followeeId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account userLogedIn = accountRepository.findByUsername(auth.getName());
        Account followee = accountRepository.getOne(followeeId);
        boolean alreadyFollowing = userLogedIn.getFollowingPeople()
                .stream()
                .anyMatch(followingDetail -> Objects.equals(followingDetail.getFolloweeId(), followeeId));
        
        if(!alreadyFollowing){
           FollowingDetail followingDetail = new FollowingDetail(followeeId, null, userLogedIn, LocalDate.now());
           followingDetailRepository.save(followingDetail);
           return new Notification(true, "You have started to follow " + followee.getFirstName() + " " + followee.getLastName());
        }
        
        return new Notification(false, "You are already following " + followee.getFirstName() + " " + followee.getLastName());
    }
}
