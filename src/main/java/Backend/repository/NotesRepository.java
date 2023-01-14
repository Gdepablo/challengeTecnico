package Backend.repository;


import Backend.component.Notes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends CrudRepository<Notes,Integer>

    //Este repo se encarga de la persistencia de notas y toda la cosa.
        // Si yo necesito agregar mas metodos 'custom' con implementacion, tengo q crear otra interf aparte que no hereda de CRUD, y despues llamar a CRUD.
{
//No pongo findByID porque viene por defecto.

@Query("select n from Notes n where n.active = true")
List<Notes> findAllActive();

@Query("select n from Notes n where n.active = false") //Asi sea privado el atributo, JPA usa reflexion entonces puede acceder.
List<Notes> findAllArchived();

//El repo no hace logica, solo plantea los métodos abstractos q en teoria SPRING maneja. Yo en los services tengo mi logica.
}