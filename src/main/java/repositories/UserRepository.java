package repositories;

import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.userAccount.id = ?1")
    User findByUserAccountId(int userAccountId);
}
