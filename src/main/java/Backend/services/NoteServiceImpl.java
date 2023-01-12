package Backend.services;
import Backend.Helper.MHelpers;
import Backend.component.Notes;
import Backend.component.NotesDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import Backend.repository.NotesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NotesRepository notesRepository;


    @Override
    public NotesDTO getNotesById(int id) {
        Optional<Notes> notes = notesRepository.findById(id);
        Optional<NotesDTO> note = notes.map(this::convertToNotesDTO); //Mapeo para convertir de Notes a NotesDTO
        return this.noOptionalNoteDTO(note);
    }

    public NotesDTO noOptionalNoteDTO(Optional<NotesDTO> note) {
        return note.stream().toList().get(0); //Pasamanos para sacarme el optional del JPARepository
    }

    public Notes noOptionalNote(Optional<Notes> note) {
        return note.stream().toList().get(0);
    }

    @Override
    public List<NotesDTO> findAllActive() {
        List<Notes> listToMap = notesRepository.findAllActive();
       return listToMap.stream().map(this::convertToNotesDTO).toList();
    }

    @Override
    public List<NotesDTO> findAll() {
        Iterable<Notes> notes = notesRepository.findAll();
        List<Notes> iterableToList = new ArrayList<Notes>();
        notes.forEach(iterableToList::add);
        List<NotesDTO> notesToRetrieve = iterableToList.stream().map(this::convertToNotesDTO).toList();
        return notesToRetrieve;

    }

    @Override
    public List<NotesDTO> findAllArchived() {
        List<Notes> listToMap = notesRepository.findAllArchived();
        return listToMap.stream().map(this::convertToNotesDTO).toList();
    }

    @Override
    public void archiveNote(int id) {
        NotesDTO note = this.getNotesById(id);
        Notes newNote = this.convertToNotes(note);
        newNote.setActive(false);
    }
    @Override
    public void unarchiveNote(int id) {
        NotesDTO note = this.getNotesById(id);
        Notes newNote = this.convertToNotes(note);
        newNote.setActive(true);
        //TODO: En el model, lo que deberíamos hacer es filtrar por activas y eso parecido a lo de vinculaciones del TP de DDS.
    }
    public NotesDTO convertToNotesDTO(final Notes note) {
        return MHelpers.modelMapper().map(note, NotesDTO.class);
    }

    public Notes convertToNotes(final NotesDTO note) {
        return MHelpers.modelMapper().map(note, Notes.class);
    }

    @Override
    public void save(NotesDTO note) {
        Notes newNote = this.convertToNotes(note);
        this.notesRepository.save(newNote);

    }

    @Override
    public void saveAll(List<NotesDTO> notesDTO) {
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
    public void updateNote(@NotNull NotesDTO newNote) {
        Optional<Notes> note = notesRepository.findById(newNote.getId()); // busco en la DB la nota a ver si existe.
        if (note != null) { //Mi idea acá es que si la nota no existe en la DB devuelva null, pero no sé bien si funciona.
            Notes noOptionalNote = this.noOptionalNote(note);
            noOptionalNote.setTitle(newNote.getTitle());
            noOptionalNote.setContent(newNote.getContent());
            noOptionalNote.addCategories(newNote.getCategories());
            notesRepository.save(noOptionalNote);}

        else {
            Notes noteToCreate;
            noteToCreate = new Notes(newNote.getTitle(), newNote.getContent());
            noteToCreate.addCategories(newNote.getCategories());
            notesRepository.save(noteToCreate);
        }
    }
}