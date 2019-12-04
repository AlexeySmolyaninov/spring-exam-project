package projekti.account;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.followers.FollowingDetail;

/**
 *
 * @author Alexey Smolyaninov
 */
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Account extends AbstractPersistable<Long>{
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    private String firstName;
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String profileName;
    
    @OneToMany(mappedBy = "follower")
    @Basic(fetch = FetchType.LAZY)
    private List<FollowingDetail> followingPeople;
}
