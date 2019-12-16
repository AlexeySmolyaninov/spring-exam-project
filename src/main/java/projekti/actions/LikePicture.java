package projekti.actions;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.account.Account;
import projekti.media.Picture;
import projekti.post.Post;

/**
 *
 * @author Alexey Smolyaninov
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LIKE_PICTURE")
public class LikePicture extends AbstractPersistable<Long>{
    
    @ManyToOne
    private Account owner;
    
    @ManyToOne
    private Picture picture;
}
