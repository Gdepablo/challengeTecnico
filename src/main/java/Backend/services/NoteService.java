package Backend.services;

import Backend.component.NoteDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {

    List<NoteDTO> findAll();

    List<NoteDTO> findAllArchived();

    void archiveNote(int id);

    void unarchiveNote(int id);

    NoteDTO getNotesById(int id);

    List<NoteDTO> findAllActive();

    void save(NoteDTO note);

    void saveAll(List<NoteDTO> note);

    void deleteById(int id);

    void updateNote(int id,@NotNull NoteDTO newNote);

}