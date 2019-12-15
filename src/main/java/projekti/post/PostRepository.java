package projekti.post;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.account.Account;

/**
 *
 * @author Alexey
 */
public interface PostRepository extends JpaRepository<Post, Long>{
    
    public Page<Post> findAllByOwner(Account owner, Pageable pageable);
}
