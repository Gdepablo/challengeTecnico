package Backend.services;

import Backend.component.NotesDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {


    //https://www.quora.com/Why-dont-you-have-to-implement-all-the-methods-in-an-interface-if-you-are-injecting-a-repository-class-in-Spring-Boot
    List<NotesDTO> findAll();

    List<NotesDTO> findAllArchived();

    void archiveNote(int id);

    void unarchiveNote(int id);

    NotesDTO getNotesById(int id);

    List<NotesDTO> findAllActive();

    void save(NotesDTO note);

    void saveAll(List<NotesDTO> note);

    void deleteById(int id); //Public redundante en interfaces

    void updateNote(@NotNull NotesDTO newNote);


    //Tenemos que pensar en la nota sólo como almacenamiento, igual que el DTO es un objeto de transición para no revelar
    //Mi dominio (o sea cómo modelé la nota en este caso) al usuario o a algún vivo. Entonces por eso usamos el DTO y por eso
    //es que hacemos que el repositorio se encargue de toodo.

}