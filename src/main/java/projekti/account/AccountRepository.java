package projekti.account;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alexey Smolyaninov
 */
public interface AccountRepository extends JpaRepository<Account, Long>{
    
    @EntityGraph(attributePaths = {"followingPeople"})
    public Account findByUsername(String username);
    
    @EntityGraph(attributePaths = {"followingPeople"})
    public Account findByProfileName(String profileName);
    
    public List<Account> findByFirstNameIgnoreCaseContainingAndLastNameIgnoreCaseContaining(String firstName, String lastName);
}
