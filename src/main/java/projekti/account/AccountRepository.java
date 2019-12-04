package projekti.account;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alexey Smolyaninov
 */
public interface AccountRepository extends JpaRepository<Account, Long>{
    public Account findByUsername(String username);
    public Account findByProfileName(String profileName);
    public List<Account> findByFirstNameIgnoreCaseContainingAndLastNameIgnoreCaseContaining(String firstName, String lastName);
}
