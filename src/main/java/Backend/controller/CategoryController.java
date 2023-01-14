package Backend.controller;

import Backend.Exceptions.NotFoundException;
import Backend.Exceptions.RequestBodyIsNullException;
import Backend.component.CategoryDTO;
import Backend.services.CategoryServiceImpl;
import Backend.services.NoteServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes/{noteId}/category") //No se si la ruta esta bien, o conviene otra cosa, pero es evidente que las categorias se agregan a una nota.
@Data
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
        return new ResponseEntity<>(HttpStatus.OK);

    } //OK. OK VALIDACIONES.

    //Podria poner para mostrar todas las categorias pero meeh. Es lo mismo solo q con GetMapping y eso.
}
