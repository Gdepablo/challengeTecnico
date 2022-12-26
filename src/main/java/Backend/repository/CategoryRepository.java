package Backend.repository;

import Backend.component.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer> {

    void saveAll(List<Category> categories);

    void deleteByTag(String tag);

    Optional<Category> findByTag(String tag);

}
