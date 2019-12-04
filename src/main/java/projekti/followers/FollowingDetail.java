package projekti.followers;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.account.Account;

/**
 *
 * @author Alexey Smolyaninov
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowingDetail extends AbstractPersistable<Long>{
    
    private Long followeeId;
    @Transient
    private Account followeeEntity;
    
    @ManyToOne
    private Account follower;
    
    private LocalDate startedToFollow;
    
    
}
