package projekti.media;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.account.Account;

/**
 *
 * @author Alexey Smolyaninov
 */

public interface PictureRepository extends JpaRepository<Picture, Long>{
    
    List<Picture> findAllByOwner(Account owner);
    
}
