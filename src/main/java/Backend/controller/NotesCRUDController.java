package Backend.controller;

import Backend.Helper.MHelpers;
import Backend.component.Category;
import Backend.component.Notes;
import Backend.component.NotesDTO;
import Backend.services.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Backend.services.NoteServiceImpl;
import spark.ModelAndView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin("*") //Según GPT ai es para poder tomar datos , por ej si mi pag web está en google.com, de api.google.com. Es seguro sólo en app publicas
@RestController
@RequestMapping("/notes")
public class NotesCRUDController {


    private final NoteServiceImpl service;
    private final CategoryServiceImpl categoryService;

    @Autowired
    public NotesCRUDController(NoteServiceImpl service, CategoryServiceImpl categoryService) {
        this.service= service;
        this.categoryService = categoryService;
    } //Si bien tiene el autowired, esto es para hacerlos final tambien.
    //https://stackoverflow.com/questions/34580033/spring-io-autowired-the-blank-final-field-may-not-have-been-initialized

    @PostMapping("/new")
    public ResponseEntity<Notes> newNote(@RequestBody Notes note) { //El request body es importante para poder recibir la nota.
        NotesDTO aNote = MHelpers.modelMapper().map(note,NotesDTO.class);
        service.save(aNote);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Notes> deleteNote(@PathVariable int id) {
       service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Notes> updateNote(@PathVariable int id, @RequestBody Notes aNote) {
        NotesDTO noteDTO = service.getNotesById(id);
        if (noteDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // update note
        service.updateNote(noteDTO);

        Notes updatedNote = MHelpers.modelMapper().map(noteDTO,Notes.class);
        // map Note to NoteDTO
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    @PostMapping("/archive/{id}")
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Notes> archiveNote(@PathVariable int id) {
        service.archiveNote(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(NullPointerException.class)
    @PostMapping("/unarchive/{id}")
    public ResponseEntity<Notes> unarchiveNote(@PathVariable int id) {
        service.unarchiveNote(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/active",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotesDTO>> getActiveNotes() {
        return new ResponseEntity<>(service.findAllActive(), HttpStatus.OK);
        //TODO: ¿Esto seria un post??
    }

    @GetMapping(value="/archived",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotesDTO>> getArchivedNotes() {
        return new ResponseEntity<>(service.findAllArchived(), HttpStatus.OK);
    }
    @PostMapping({"/category/new/{id}"})
    public ResponseEntity<Notes> addCategory(@RequestBody Category category, @PathVariable int id) {
        categoryService.addCategory(id,category);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping({"/category/{id}"})
    public ResponseEntity<Notes> removeCategory(@RequestBody Category category, @PathVariable int id) {
        categoryService.removeCategory(id, category);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    /* ModelAndView getAllActiveNotes() {
        Map<String, Object> model = new HashMap<>();
        @Query("SELECT n FROM Notes n WHERE n.active = true")
        model.put("activeNotes",service.findAllActive());
        return new ModelAndView(model,"notes.hbs"); //TODO: Esto esta mal...
    }*/

}