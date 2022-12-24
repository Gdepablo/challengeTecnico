package Backend.repository;

import Backend.component.Categoria;
import Backend.component.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categoria,Integer> {

}
