package Backend.controller;

import Backend.Helper.MHelpers;
import Backend.component.Category;
import Backend.component.CategoryDTO;
import Backend.component.Notes;
import Backend.services.CategoryServiceImpl;
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

    @Autowired
    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping({"/new"})
    public ResponseEntity<Notes> addCategory(@RequestBody Category category, @PathVariable int noteId) {
        CategoryDTO aCategory = MHelpers.modelMapper().map(category,CategoryDTO.class);
        categoryService.save(aCategory);
        categoryService.addCategory(noteId,category);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    } //Esto es para asociar la categoria a una nota. OK.

    @DeleteMapping({"/delete/{idCategory}"})
    public ResponseEntity<Notes> removeCategory(@PathVariable int idCategory, @PathVariable int noteId) {
        categoryService.removeCategory(noteId, idCategory);
        return new ResponseEntity<>(HttpStatus.OK);

    } //OK.
}
