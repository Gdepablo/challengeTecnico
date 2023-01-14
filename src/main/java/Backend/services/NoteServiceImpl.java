package Backend.services;
import Backend.Exceptions.NotFoundException;
import Backend.Helper.MHelpers;
import Backend.component.Notes;
import Backend.component.NotesDTO;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import Backend.repository.NotesRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Data
public class NoteServiceImpl implements NoteService {


    private final NotesRepository notesRepository;

    @Autowired
    public NoteServiceImpl(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public NotesDTO getNotesById(int id) {
        Optional<Notes> notes = notesRepository.findById(id);
        Optional<NotesDTO> note = notes.map(this::convertToNotesDTO); //Mapeo para convertir de Notes a NotesDTO
        return this.noOptionalNoteDTO(note);
    }

    public NotesDTO noOptionalNoteDTO(@NotNull Optional<NotesDTO> note) {
        if(note.isPresent())
            return note.get(); //Para sacarme el optional
        else {
            throw new NotFoundException("Note not found");
        }
    }

    public Notes noOptionalNote(@NotNull Optional<Notes> note) {
        if(note.isPresent())
            return note.get();
        else {
            throw new NotFoundException("Note not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotesDTO> findAllActive() {
        List<Notes> listToMap = notesRepository.findAllActive();
       return listToMap.stream().map(this::convertToNotesDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotesDTO> findAll() {
        Iterable<Notes> notes = notesRepository.findAll();
        List<Notes> iterableToList = new ArrayList<>();
        notes.forEach(iterableToList::add);
        return iterableToList.stream().map(this::convertToNotesDTO).toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<NotesDTO> findAllArchived() {
        List<Notes> listToMap = notesRepository.findAllArchived();
        return listToMap.stream().map(this::convertToNotesDTO).toList();
    }

    @Override
    public void archiveNote(int id) {
        NotesDTO note = this.getNotesById(id);
        Notes newNote = this.convertToNotes(note);
        newNote.setActive(false);
        notesRepository.save(newNote); //Importante para que guarde el actualizado c:
    }
    @Override
    public void unarchiveNote(int id) {
        NotesDTO note = this.getNotesById(id);
        Notes newNote = this.convertToNotes(note);
        newNote.setActive(true);
        notesRepository.save(newNote);
    }

    private Notes convertToNotes(NotesDTO notesDTO) {
        return MHelpers.modelMapper().map(notesDTO, Notes.class);
    }

    private NotesDTO convertToNotesDTO(Notes note) {
        return MHelpers.modelMapper().map(note, NotesDTO.class);
    }

    @Override
    public void save(NotesDTO note) {
        Notes newNote = this.convertToNotes(note);
        this.notesRepository.save(newNote);

    }

    @Override
    public void saveAll(@NotNull List<NotesDTO> notesDTO) {
        List<Notes> newNotes = notesDTO.stream().map(this::convertToNotes).toList();
        this.notesRepository.saveAll(newNotes);
    }

    @Override
    public void deleteById(int id) {
        Optional<Notes> noteDTO = notesRepository.findById(id);
        Notes note = this.noOptionalNote(noteDTO);
        notesRepository.delete(note);
    }


    @Override
    public void updateNote(int id, @NotNull NotesDTO newNote) { //ID de la nota activa.
        Optional<Notes> note = notesRepository.findById(id);
        if(note.isEmpty()) {throw new NotFoundException("Note not found");}
        else {
        Notes noOptionalNote = noOptionalNote(note);
        noOptionalNote.setTitle(noOptionalNote.getTitle() + newNote.getTitle());
        noOptionalNote.setContent(noOptionalNote.getContent() +newNote.getContent());
        noOptionalNote.addCategories(newNote.getCategories());
        notesRepository.save(noOptionalNote);
    }}}


//WARNING: Las tildes rompen el debug, no usar tildes pq crashea maven.