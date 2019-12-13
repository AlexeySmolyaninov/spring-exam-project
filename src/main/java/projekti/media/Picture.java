package projekti.media;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.Lob;
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
    
    @Lob
    private byte[] content;
    
    private String description;
    
    @ManyToOne
    private Account owner;
}
