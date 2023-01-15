package Backend.repository;


import Backend.component.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note,Integer>

    //Este repo se encarga de la persistencia de notas y toda la cosa.
        // Si yo necesito agregar mas metodos 'custom' con implementacion, tengo q crear otra interf aparte que no hereda de CRUD, y despues llamar a CRUD.
{
//No pongo findByID porque viene por defecto.

@Query("select n from Note n where n.active = true")
@Transactional(readOnly = true)
List<Note> findAllActive();

@Query("select n from Note n where n.active = false") //Asi sea privado el atributo, JPA usa reflexion entonces puede acceder.
@Transactional(readOnly = true)
List<Note> findAllArchived();


//El repo no hace logica, solo plantea los métodos abstractos q en teoria SPRING maneja. Yo en los services tengo mi logica.
}