package projekti.actions;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.media.Picture;

/**
 *
 * @author Alexey Smolyaninov
 */
public interface LikePictureRepository extends JpaRepository<LikePicture, Long>{
    
    public List<LikePicture> findAllByPicture(Picture picture);
}
