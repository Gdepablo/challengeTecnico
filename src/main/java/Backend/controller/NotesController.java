package Backend.controller;

import Backend.Exceptions.RequestBodyIsNullException;
import Backend.component.NoteDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import Backend.services.NoteServiceImpl;
import java.util.List;

@RestController
@RequestMapping("/notes")
@Data
@CrossOrigin(origins = "http://localhost:4200")
public class NotesController {

    private final NoteServiceImpl service;

    @Autowired
    public NotesController(NoteServiceImpl service) {
        this.service= service;
    }

    @PostMapping("/new")
    public ResponseEntity<NoteDTO> newNote(@RequestBody(required = false) NoteDTO note) {
        if (note == null) {
            throw new RequestBodyIsNullException("Request body is null");
        }
        service.save(note);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<NoteDTO> deleteNote(@PathVariable int id) {
       service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable int id, @RequestBody(required = false) NoteDTO aNote) {
        if (aNote == null) {
            throw new RequestBodyIsNullException("Request body is null");
        }
        service.updateNote(id,aNote);
        return new ResponseEntity<>(aNote, HttpStatus.OK);}


    @PutMapping("/archive/{id}")
    public ResponseEntity<NoteDTO> archiveNote(@PathVariable int id) {
        service.archiveNote(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/unarchive/{id}")
    public ResponseEntity<NoteDTO> unarchiveNote(@PathVariable int id) {
        service.unarchiveNote(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/active",produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<List<NoteDTO>> getActiveNotes() {
        return new ResponseEntity<>(service.findAllActive(), HttpStatus.OK);
    }

    @GetMapping(value="/archived",produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<List<NoteDTO>> getArchivedNotes() {
        return new ResponseEntity<>(service.findAllArchived(), HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable int id) {
        return new ResponseEntity<>(service.getNotesById(id), HttpStatus.OK);
    }
}