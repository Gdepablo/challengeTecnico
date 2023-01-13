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
    public ResponseEntity<Notes> newNote(@RequestBody Notes note) { //El request body es importante para poder recibir la nota.
        NotesDTO aNote = MHelpers.modelMapper().map(note,NotesDTO.class);
        service.save(aNote);
        return new ResponseEntity<>(HttpStatus.CREATED);
    } //OK

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Notes> deleteNote(@PathVariable int id) {
       service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } //OK

    @PutMapping("/update/{id}")
    public ResponseEntity<Notes> updateNote(@PathVariable int id, @RequestBody Notes aNote) {
        try {NotesDTO noteDTO = service.getNotesById(id);
        // update note
        NotesDTO mappedNote = MHelpers.modelMapper().map(aNote,NotesDTO.class);
        service.updateNote(id,mappedNote);

        Notes updatedNote = MHelpers.modelMapper().map(noteDTO,Notes.class);
        // map Note to NoteDTO
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);}
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/archive/{id}")
    public ResponseEntity<Notes> archiveNote(@PathVariable int id) {
        service.archiveNote(id);
        return new ResponseEntity<>(HttpStatus.OK);
    } //OK

    @PostMapping("/unarchive/{id}")
    public ResponseEntity<Notes> unarchiveNote(@PathVariable int id) {
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

    /* ModelAndView getAllActiveNotes() {
        Map<String, Object> model = new HashMap<>();
        @Query("SELECT n FROM Notes n WHERE n.active = true")
        model.put("activeNotes",service.findAllActive());
        return new ModelAndView(model,"notes.hbs"); //TODO: Esto esta mal...
    }*/

    //TODO: Desacoplar las categorias de este controller, le corresponde a otro controller. Manejar excepciones.

}