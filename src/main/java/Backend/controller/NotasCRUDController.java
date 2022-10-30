package Backend.controller;

import Backend.component.Nota;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Backend.services.NotaServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotasCRUDController {
    private final NotaServiceImpl service;


    public NotasCRUDController(NotaServiceImpl service) {
        this.service= service;
    }

    @PostMapping("/nueva")
    ResponseEntity<Nota> nuevaNota(@RequestBody Nota nota) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ExceptionHandler(NullPointerException.class)
    ResponseEntity<Nota> borrarNota(@PathVariable int id) {
       service.borrarNota(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/archivar")
    @ExceptionHandler(NullPointerException.class)
    ResponseEntity<Nota> archivarNota(@PathVariable int id) {
        service.archivarNota(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(NullPointerException.class)
    @PostMapping("/desarchivar")
    ResponseEntity<Nota> desarchivarNota(@PathVariable int id) {
        service.desarchivarNota(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/")
    ResponseEntity<List<Nota>> notasActivas() {
        return new ResponseEntity<>(service.getAllNotasActivas(), HttpStatus.OK);
    }

    @GetMapping("/archivadas")
    ResponseEntity<List<Nota>> notasArchivadas() {
        return new ResponseEntity<>(service.getAllNotasArchivadas(), HttpStatus.OK);
    }
    @PostMapping({"/categoria/{id}"})
    ResponseEntity<Nota> agregarCategoria(@RequestBody String categoria, @PathVariable int id) {
        service.agregarCategoria(id, categoria);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping({"/categoria/{id}"})
    ResponseEntity<Nota> quitarCategoria(@RequestBody String categoria, @PathVariable int id) {
        service.removerCategoria(id, categoria);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO: Revisar lo de los mapping para el archivar y desarchivar
    @PutMapping("/{id}")
    public void actualizarNota() {}
    //TODO: Toca pensarlo más, mepa que no es así. Según el video, se hace desde el front??*/

}