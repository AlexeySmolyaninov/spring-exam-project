package projekti.media;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
@NoArgsConstructor
@AllArgsConstructor
public class Picture extends AbstractPersistable<Long>{
    
    private byte[] content;
    
    private boolean isProfilePicture = false;
    
    @ManyToOne
    private Account owner;
}
