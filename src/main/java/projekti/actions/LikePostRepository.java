package projekti.actions;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.post.Post;

/**
 *
 * @author Alexey Smolyaninov
 */
public interface LikePostRepository extends JpaRepository<LikePost, Long>{
    
    public List<LikePost> findAllByPost(Post post); 
}
