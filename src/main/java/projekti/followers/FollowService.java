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
import projekti.account.AccountService;

/**
 *
 * @author Alexey Smolyaninov
 */
@Service
public class FollowService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private FollowingDetailRepository followingDetailRepository;
    
    
    public Notification startFollowingAccount(Long followeeId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account follower = accountRepository.findByUsername(auth.getName());
        Account followee = accountRepository.getOne(followeeId);
        boolean alreadyFollowing = follower.getFollowingPeople()
                .stream()
                .anyMatch(followingDetail -> Objects.equals(followingDetail.getFollowee().getId(), followeeId));
        
        if(!alreadyFollowing){
           FollowingDetail followingDetail = new FollowingDetail(followee, follower, LocalDate.now());
           followingDetailRepository.save(followingDetail);
           return new Notification(true, "You have started to follow " + followee.getFirstName() + " " + followee.getLastName());
        }
        
        return new Notification(false, "You are already following " + followee.getFirstName() + " " + followee.getLastName());
    }
    
    public Notification unfollowAccount(Long followeeId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account logedInUser = accountService.findAccountByUsername(auth.getName());
        FollowingDetail followingDetail = logedInUser.getFollowingPeople()
                .stream()
                .filter(myFollowee -> Objects.equals(myFollowee.getFollowee().getId(), followeeId))
                .findFirst()
                .orElse(null);
        if(followingDetail != null){
            followingDetailRepository.delete(followingDetail);
            return new Notification(true, "You have successfully unfollowed an account!");
        } else {
            return new Notification(false, "You haven't unfollowed an account!");
        }
    }
}
