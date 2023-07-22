package Backend.repository;

import Backend.component.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer> {

    Optional<Category> findByTag(String tag);

}
