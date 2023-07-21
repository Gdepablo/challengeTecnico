package Backend.repository;


import Backend.component.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note,Integer>

{

@Query("select n from Note n where n.active = true")
@Transactional(readOnly = true)
List<Note> findAllActive();

@Query("select n from Note n where n.active = false")
@Transactional(readOnly = true)
List<Note> findAllArchived();

}