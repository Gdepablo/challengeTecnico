package Backend.controller;

import Backend.Helper.MHelpers;
import Backend.component.Category;
import Backend.component.Notes;
import Backend.component.NotesDTO;
import Backend.services.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Backend.services.NoteServiceImpl;
import spark.ModelAndView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*") //Desconozco para qué es.
@RestController
@RequestMapping("/notes")
public class NotesCRUDController {

    @Autowired
    private final NoteServiceImpl service;

    @Autowired
    private final CategoryServiceImpl categoryService;


    public NotesCRUDController(NoteServiceImpl service, CategoryServiceImpl categoryService) {
        this.service= service;
        this.categoryService = categoryService;
    } //Si bien tiene el autowired, esto es para hacerlos final tambien.
    //https://stackoverflow.com/questions/34580033/spring-io-autowired-the-blank-final-field-may-not-have-been-initialized

    @PostMapping("/new")
    public ResponseEntity<Notes> newNote(@RequestBody Notes note) {
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
    public ResponseEntity<Notes> updateNote(@PathVariable int id) {
        NotesDTO note = MHelpers.modelMapper().map(service.getNotesById(id), NotesDTO.class);
        service.updateNote(note);
        return new ResponseEntity<>(HttpStatus.OK);

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
    @GetMapping(value="/active",produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAllActiveNotes() {
        Map<String, Object> model = new HashMap<>();
        model.put("activeNotes",service.findAllActive());
        return new ModelAndView(model,"notes.hbs");
    }
    @GetMapping(value="/home")
    public ModelAndView getForm() {
        return new ModelAndView(null, "new-note.hbs");
    }

    //Tengo que investigar bien el tema de Thyrmleaf y/o handlebars, no sé si vale la pena porque spark y spring no funcionan bien.
    //Sé q se puede hbs con spring pero capaz es mejor el otro.
    //Revisar tema handlebars, faltan vistas, y no sé si no faltan métodos de get y post tambien

    //TODO: Fix routes, no me queda del todo claro cuál sería get y cuál post en este caso.
    //https://www.youtube.com/watch?v=TWtdxmWYsFA&list=PLRHPC9shBXl1Jhb2XfxUEjjUMKcYM9odm&index=9
    //Mirar el video, estoy pensando en si realmente vale la pena el userRequest o no, la verdad no sé. Además averiguar combinar spark y spring y tal.
    //EDIT: En realidad no sé cómo pegarle a las handlebars desde spring, no significa que las routes estén mal.

}