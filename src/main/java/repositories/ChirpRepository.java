package repositories;

import domain.Article;
import domain.Chirp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp,Integer> {

    @Query("select c from Chirp c where c.user.id = ?1")
    Collection<Chirp> findChirpsByUserId(int userId);

    @Query("select u.chirps from User u join u.followings f where u.id =?1")
    Collection<Chirp> findAllChirpsByFollowings(int userId);
}
