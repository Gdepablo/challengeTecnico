package Backend.repository;


import Backend.component.Notes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotesRepository extends CrudRepository<Notes,Integer>

    //Este repo se encarga de la persistencia de notas y toda la cosa.
        // Si yo necesito agregar más métodos 'custom' con implementación, tengo q crear otra interf aparte que no hereda de CRUD, y después llamar a CRUD.
{
//No pongo findByID porque viene por defecto.

@Query("select n from #{Nota} n where n.active = true")
@Transactional(readOnly = true)
List<Notes> findAllActive();

@Query("select n from #{Nota} n where n.active = false")
@Transactional(readOnly = true)
List<Notes> findAllArchived();

//El repo no hace lógica, sólo plantea los métodos abstractos q en teoría SPRING maneja. Yo en los services tengo mi logica.
}