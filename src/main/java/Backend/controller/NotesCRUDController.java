package Backend.controller;

import Backend.Helper.MHelpers;

import Backend.component.Notes;
import Backend.component.NotesDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Backend.services.NoteServiceImpl;

import java.util.List;


//@CrossOrigin("*") //Segun GPT ai es para poder tomar datos , por ej si mi pag web esta en google.com, de api.google.com. Es seguro solo en app publicas
@RestController
@RequestMapping("/notes")
@Data
public class NotesCRUDController {

    private final NoteServiceImpl service;

    @Autowired
    public NotesCRUDController(NoteServiceImpl service) {
        this.service= service;
    } //Si bien tiene el autowired, esto es para hacerlos final tambien.
    //https://stackoverflow.com/questions/34580033/spring-io-autowired-the-blank-final-field-may-not-have-been-initialized

    @PostMapping("/new")
    public ResponseEntity<NotesDTO> newNote(@RequestBody NotesDTO note) { //El request body es importante para poder recibir la nota.
        service.save(note); //Segun GPT Ai es mejor que reciba notesDTO que es el transfer object a pasarle la note directo.
        return new ResponseEntity<>(HttpStatus.CREATED);
    } //OK

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<NotesDTO> deleteNote(@PathVariable int id) {
       service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } //OK

    @PutMapping("/update/{id}")
    public ResponseEntity<NotesDTO> updateNote(@PathVariable int id, @RequestBody NotesDTO aNote) {
        try {NotesDTO noteDTO = service.getNotesById(id);
        // update note
        service.updateNote(id,aNote);
        // map Note to NoteDTO
        return new ResponseEntity<>(aNote, HttpStatus.OK);}
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/archive/{id}")
    public ResponseEntity<NotesDTO> archiveNote(@PathVariable int id) {
        service.archiveNote(id);
        return new ResponseEntity<>(HttpStatus.OK);
    } //OK

    @PostMapping("/unarchive/{id}")
    public ResponseEntity<NotesDTO> unarchiveNote(@PathVariable int id) {
        service.unarchiveNote(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    } //OK

    @GetMapping(value = "/active",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotesDTO>> getActiveNotes() {
        return new ResponseEntity<>(service.findAllActive(), HttpStatus.OK); //OK
    } //OK

    @GetMapping(value="/archived",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotesDTO>> getArchivedNotes() {
        return new ResponseEntity<>(service.findAllArchived(), HttpStatus.OK); //OK
    } //OK

    //TODO: Manejar excepciones

}