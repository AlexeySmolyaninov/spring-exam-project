/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.followers;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alexey
 */
public interface FollowingDetailRepository extends JpaRepository<FollowingDetail, Long>{
    
}
