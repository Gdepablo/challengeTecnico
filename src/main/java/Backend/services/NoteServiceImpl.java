package Backend.services;
import Backend.Exceptions.NotFoundException;
import Backend.Helper.MHelpers;
import Backend.component.Note;
import Backend.component.NoteDTO;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import Backend.repository.NoteRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Data
public class NoteServiceImpl implements NoteService {


    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public NoteDTO getNotesById(int id) {
        Optional<Note> notes = noteRepository.findById(id);
        Optional<NoteDTO> note = notes.map(this::convertToNotesDTO);
        return this.noOptionalNoteDTO(note);
    }

    public NoteDTO noOptionalNoteDTO(@NotNull Optional<NoteDTO> note) {
        if(note.isPresent())
            return note.get();
        else {
            throw new NotFoundException("Note not found");
        }
    }

    public Note noOptionalNote(@NotNull Optional<Note> note) {
        if(note.isPresent())
            return note.get();
        else {
            throw new NotFoundException("Note not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteDTO> findAllActive() {
        List<Note> listToMap = noteRepository.findAllActive();
       return listToMap.stream().map(this::convertToNotesDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteDTO> findAll() {
        Iterable<Note> notes = noteRepository.findAll();
        List<Note> iterableToList = new ArrayList<>();
        notes.forEach(iterableToList::add);
        return iterableToList.stream().map(this::convertToNotesDTO).toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteDTO> findAllArchived() {
        List<Note> listToMap = noteRepository.findAllArchived();
        return listToMap.stream().map(this::convertToNotesDTO).toList();
    }

    @Override
    public void archiveNote(int id) {
        NoteDTO note = this.getNotesById(id);
        Note newNote = this.convertToNotes(note);
        newNote.setActive(false);
        noteRepository.save(newNote);
    }
    @Override
    public void unarchiveNote(int id) {
        NoteDTO note = this.getNotesById(id);
        Note newNote = this.convertToNotes(note);
        newNote.setActive(true);
        noteRepository.save(newNote);
    }

    public Note convertToNotes(NoteDTO noteDTO) {
        System.out.println("MAPPER");
        return MHelpers.modelMapper().map(noteDTO, Note.class);

    }

    public NoteDTO convertToNotesDTO(Note note) {
        return MHelpers.modelMapper().map(note, NoteDTO.class);
    }

    @Override
    public void save(NoteDTO note) { //Se le pasan las categorias como un JSON con un tag adentro.
        Note newNote = this.convertToNotes(note);
        this.noteRepository.save(newNote);

    }

    @Override
    public void saveAll(@NotNull List<NoteDTO> noteDTO) {
        List<Note> newNotes = noteDTO.stream().map(this::convertToNotes).toList();
        this.noteRepository.saveAll(newNotes);
    }

    @Override
    public void deleteById(int id) {
        Optional<Note> noteDTO = noteRepository.findById(id);
        Note note = this.noOptionalNote(noteDTO);
        noteRepository.delete(note);
    }

    @Override
    public void updateNote(int id, @NotNull NoteDTO newNote) {
        Optional<Note> note = noteRepository.findById(id);
        if(note.isEmpty()) {throw new NotFoundException("Note not found");}
        else {
        Note noOptionalNote = noOptionalNote(note);
        noOptionalNote.setTitle(newNote.getTitle());
        noOptionalNote.setContent(newNote.getContent());
        noOptionalNote.updateCategories(newNote.getCategories());
        noteRepository.save(noOptionalNote);
    }}}