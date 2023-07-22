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


//arroba CrossOrigin("*") //Segun GPT ai es para poder tomar datos , por ej si mi pag web esta en google.com, de api.google.com. Es seguro solo en app publicas
@RestController
@RequestMapping("/notes")
@Data
@CrossOrigin(origins = "http://localhost:4200")
public class NotesController {

    private final NoteServiceImpl service;

    //IMPORTANTE: No valido si PathVariable es nulo porque ya se valida
    // automaticamente cuando se llaman los metodos para transformar en NotasDTO, CategoryDTO,etc.

    @Autowired
    public NotesController(NoteServiceImpl service) {
        this.service= service;
    } //Si bien tiene el autowired, esto es para hacerlos final tambien. Es mejor hacerlos final y poner autowired en el constructor (lo aprendi de la GPT AI)
    //https://stackoverflow.com/questions/34580033/spring-io-autowired-the-blank-final-field-may-not-have-been-initialized

    @PostMapping("/new")
    public ResponseEntity<NoteDTO> newNote(@RequestBody(required = false) NoteDTO note) { //El request body es importante para poder recibir la nota.
        //Agrego el required false porque el default es true, o sea que si es null o no es correcto ponele, tira su propia excepcion y no la mia.
        if (note == null) {
            throw new RequestBodyIsNullException("Request body is null"); //Lo atrapa el ControllerAdvice y devuelve un bad request.
        }
        service.save(note); //Segun GPT Ai es mejor que reciba notesDTO que es el transfer object a pasarle la note directo.
        //Si no le pasas title y content crea la nota vacia. No se si esta bien, capaz podriamos hacer alguna validacion.
        return new ResponseEntity<>(HttpStatus.CREATED);
    } //OK. OK VALIDACIONES.

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<NoteDTO> deleteNote(@PathVariable int id) {
       service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } //OK. OK VALIDACIONES.

    @PutMapping("/update/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable int id, @RequestBody(required = false) NoteDTO aNote) {
        if (aNote == null) {
            throw new RequestBodyIsNullException("Request body is null");
        }

        service.updateNote(id,aNote);
        return new ResponseEntity<>(aNote, HttpStatus.OK);} //OK. OK VALIDACIONES.


    @PutMapping("/archive/{id}")
    public ResponseEntity<NoteDTO> archiveNote(@PathVariable int id) {
        service.archiveNote(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    } //OK. OK VALIDACIONES.

    @PutMapping("/unarchive/{id}")
    public ResponseEntity<NoteDTO> unarchiveNote(@PathVariable int id) {
        service.unarchiveNote(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    } //OK. OK VALIDACIONES.

    @GetMapping(value = "/active",produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<List<NoteDTO>> getActiveNotes() {
        return new ResponseEntity<>(service.findAllActive(), HttpStatus.OK); //OK
    } //OK

    @GetMapping(value="/archived",produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<List<NoteDTO>> getArchivedNotes() {
        return new ResponseEntity<>(service.findAllArchived(), HttpStatus.OK); //OK
    } //OK

    //Asumo que los get method no pueden fallar. Seria estupido que fallen a menos que haya algun error en mi codigo tipo loop infinito.

    //Los transactional read only son para mas performance segun lei en stack overflow.
    //Para filtrar notas por categoria necesitaria un metodo que me traiga TODAS las notas y de ahi tirarle un contains a la lista y tal para ver el tema de si tiene la
    //categoria o no. Supongo que el tema seria traer todas las notas de la db? O tal vez traer solo la especificada. Si traigo todas despues muestro una nomas y eso es una cosa
    //de la view que es como una pavada, es parecido al tp de dds.
    //respecto al agregar o sacar notas, hay que preguntar (porque es una decision de disenio):
    //Al sacar una categoria de una nota, la borro de la base de datos? o hago un condicional que establezca que si esa categoria solo estaba en esa nota, la borre.
    //Luego, lo mismo con el crear.

    //RequestParam is used to extract query parameters from the request URL,
    // for example, in a request like http://example.com/api/users?name=value,
    // the parameter "name" would be extracted using @RequestParam.

    //PathVariable is used to extract values from the URI path template,
    //for example, in a request like http://example.com/api/users/{id}, the value of "id" would be extracted
    // using @PathVariable.

    //OAuth2 segun dice la IA es para habilitar third-party apps sin necesidad de darle la contrasenia.
}