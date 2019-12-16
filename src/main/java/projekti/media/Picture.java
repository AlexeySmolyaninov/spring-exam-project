package projekti.media;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.account.Account;
import projekti.actions.LikePicture;

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
    @Type(type = "org.hibernate.type.BinaryType")
    @JsonIgnore
    private byte[] content;
    
    private String description;
    
    @ManyToOne
    private Account owner;
    
    @OneToMany(mappedBy = "picture", cascade = CascadeType.ALL)
    private List<LikePicture> likes;
}
