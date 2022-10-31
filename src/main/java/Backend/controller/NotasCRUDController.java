package Backend.controller;

import Backend.component.Nota;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Backend.services.NotaServiceImpl;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notas")
public class NotasCRUDController {
    private final NotaServiceImpl service;


    public NotasCRUDController(NotaServiceImpl service) {
        this.service= service;
    }

    @PostMapping("/nueva")
    public ResponseEntity<Nota> nuevaNota(@RequestBody Nota nota) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/borrar/{id}")
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Nota> borrarNota(@PathVariable int id) {
       service.borrarNota(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Nota> actualizarNota(@PathVariable int id) {
        Nota unaNota = service.getNotasById(id);
        service.actualizarNota(unaNota);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/archivar")
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Nota> archivarNota(@PathVariable int id) {
        service.archivarNota(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(NullPointerException.class)
    @PostMapping("/desarchivar")
    public ResponseEntity<Nota> desarchivarNota(@PathVariable int id) {
        service.desarchivarNota(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Nota>> notasActivas() {
        return new ResponseEntity<>(service.getAllNotasActivas(), HttpStatus.OK);
    }

    @GetMapping("/archivadas")
    public ResponseEntity<List<Nota>> notasArchivadas() {
        return new ResponseEntity<>(service.getAllNotasArchivadas(), HttpStatus.OK);
    }
    @PostMapping({"/categoria/{id}"})
    public ResponseEntity<Nota> agregarCategoria(@RequestBody String categoria, @PathVariable int id) {
        service.agregarCategoria(id, categoria);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping({"/categoria/{id}"})
    public ResponseEntity<Nota> quitarCategoria(@RequestBody String categoria, @PathVariable int id) {
        service.removerCategoria(id, categoria);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ModelAndView getAllNotasActivas() {
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("notasActivas",service.getAllNotasActivas());
        return new ModelAndView(modelo,"notas.hbs");
    }

    public ModelAndView getFormularioCreacion() {
        return new ModelAndView(null, "new-note.hbs");
    }

}