package Backend.repository;

import Backend.component.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    @EntityGraph(attributePaths = {"notes"}) //Mas eficiente que usar transactional porque le decis que loadee esto lazy.
    //y no andamos cambiando cosas q funcionan.
    User findByUsername(String aUsername);

    //findAll creo q viene por default tonces es al pedo declararlo.
}
