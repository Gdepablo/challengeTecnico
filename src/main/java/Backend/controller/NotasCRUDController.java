package Backend.controller;

import Backend.component.Notes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Backend.services.NoteServiceImpl;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notas")
public class NotasCRUDController {
    private final NoteServiceImpl service; //TODO FIX CONTROLLER. Y esto creo que es un autowired.


    public NotasCRUDController(NoteServiceImpl service) {
        this.service= service;
    }

    @PostMapping("/nueva")
    public ResponseEntity<Notes> nuevaNota(@RequestBody Notes note) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/borrar/{id}")
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Notes> borrarNota(@PathVariable int id) {
       service.borrarNota(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Notes> actualizarNota(@PathVariable int id) {
        Notes unaNota = service.getNotesById(id);
        service.actualizarNota(unaNota);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/archivar")
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Notes> archivarNota(@PathVariable int id) {
        service.archiveNote(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(NullPointerException.class)
    @PostMapping("/desarchivar")
    public ResponseEntity<Notes> desarchivarNota(@PathVariable int id) {
        service.unarchiveNote(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Notes>> notasActivas() {
        return new ResponseEntity<>(service.getAllActiveNotes(), HttpStatus.OK);
    }

    @GetMapping("/archivadas")
    public ResponseEntity<List<Notes>> notasArchivadas() {
        return new ResponseEntity<>(service.getAllActiveNotes(), HttpStatus.OK);
    }
    @PostMapping({"/categoria/{id}"})
    public ResponseEntity<Notes> agregarCategoria(@RequestBody String category, @PathVariable int id) {
        service.agregarCategoria(id, category); //TODO: FIX
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping({"/categoria/{id}"})
    public ResponseEntity<Notes> quitarCategoria(@RequestBody String categoria, @PathVariable int id) {
        service.removerCategoria(id, categoria);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ModelAndView getAllNotasActivas() {
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("notasActivas",service.getAllActiveNotes());
        return new ModelAndView(modelo,"notas.hbs");
    }

    public ModelAndView getFormularioCreacion() {
        return new ModelAndView(null, "new-note.hbs");
    }

}