package projekti.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.followers.FollowingDetail;
import projekti.media.Picture;
import projekti.post.Post;

/**
 *
 * @author Alexey Smolyaninov
 */
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Account extends AbstractPersistable<Long>{
    
    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    private String firstName;
    
    private String lastName;

    @Column(unique = true)
    private String profileName;
    
    @JsonIgnore
    @OneToMany(mappedBy = "follower")
    private List<FollowingDetail> followingPeople;
    
    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Picture> pictures;
    
    @JsonIgnore
    @OneToOne
    private Picture profilePicture;
    
    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Post> myPosts; 
}
