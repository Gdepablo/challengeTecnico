package Backend.repository;

import Backend.component.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Client,Integer> {

    @EntityGraph(attributePaths = {"notes"})
    Client findByUsername(String aUsername);

}
