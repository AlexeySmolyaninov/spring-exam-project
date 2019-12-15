package projekti.post;

import java.time.LocalDateTime;
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
public class Post extends AbstractPersistable<Long>{
    
    private String text;
    
    private LocalDateTime created;
    
    @ManyToOne
    private Account owner;
}
