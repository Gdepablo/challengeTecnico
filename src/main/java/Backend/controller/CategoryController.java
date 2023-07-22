package Backend.controller;

import Backend.Exceptions.RequestBodyIsNullException;
import Backend.component.Category;
import Backend.component.CategoryDTO;
import Backend.component.Note;
import Backend.component.NoteDTO;
import Backend.services.CategoryServiceImpl;
import Backend.services.NoteServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes/{noteId}/category") //No se si la ruta esta bien, o conviene otra cosa, pero es evidente que las categorias se agregan a una nota.
@Data
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private final NoteServiceImpl service;

    @Autowired
    public CategoryController(CategoryServiceImpl categoryService, NoteServiceImpl service) {
        this.categoryService = categoryService;
        this.service = service;
    }

   @PostMapping({"/new"})
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody(required = false) CategoryDTO categoryDTO, @PathVariable int noteId) {

       if (categoryDTO == null) {
           throw new RequestBodyIsNullException("Request body is null");
       }
        categoryService.addCategory(noteId,categoryDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    } //Esto es para asociar la categoria a una nota. OK. OK VALIDACIONES.


    @DeleteMapping({"/delete/{idCategory}"})
    public ResponseEntity<CategoryDTO> removeCategory(@PathVariable int idCategory, @PathVariable int noteId) {
        categoryService.removeCategory(noteId, idCategory);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } //OK. OK VALIDACIONES.

    @GetMapping("/filter/{categoryId}")
    public ResponseEntity<List<NoteDTO>> filterByCategory(@PathVariable int categoryId) { //No uso el id de la nota pero poco importa.
        List<NoteDTO> noteDTO = service.findAll();
        List<Note> allNotes = noteDTO.stream().map(service::convertToNotes).toList();
        Category category = categoryService.convertToCategory(categoryService.getCategoryById(categoryId));
        List<Note> noteFiltered = allNotes.stream().filter(note -> note.getCategories().contains(category)).toList();
        return new ResponseEntity<>(noteFiltered.stream().map(service::convertToNotesDTO).toList(),HttpStatus.OK);

    }

    //Podria poner para mostrar todas las categorias pero meeh. Es lo mismo solo q con GetMapping y eso.
}
