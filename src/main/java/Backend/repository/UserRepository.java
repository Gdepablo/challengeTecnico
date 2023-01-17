package Backend.repository;

import Backend.component.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    @Query("select u from User u where u.username = :aUsername")
    User findByUsername(String aUsername);

    //findAll creo q viene por default tonces es al pedo declararlo.
}
