package Backend.services;

import Backend.component.NoteDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {


    //https://www.quora.com/Why-dont-you-have-to-implement-all-the-methods-in-an-interface-if-you-are-injecting-a-repository-class-in-Spring-Boot
    List<NoteDTO> findAll();

    List<NoteDTO> findAllArchived();

    void archiveNote(int id);

    void unarchiveNote(int id);

    NoteDTO getNotesById(int id);

    List<NoteDTO> findAllActive();

    void save(NoteDTO note);

    void saveAll(List<NoteDTO> note);

    void deleteById(int id); //Public redundante en interfaces

    void updateNote(int id,@NotNull NoteDTO newNote);



    //Tenemos que pensar en la nota solo como almacenamiento, igual que el DTO es un objeto de transicion para no revelar
    //Mi dominio (o sea como modele la nota en este caso) al usuario o a algun vivo. Entonces por eso usamos el DTO y por eso
    //es que hacemos que el repositorio se encargue de toodo.

}