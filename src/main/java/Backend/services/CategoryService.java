package Backend.services;

import Backend.component.Category;
import Backend.component.CategoryDTO;
import Backend.component.NotesDTO;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface CategoryService {
    //https://www.quora.com/Why-dont-you-have-to-implement-all-the-methods-in-an-interface-if-you-are-injecting-a-repository-class-in-Spring-Boot

    List<CategoryDTO> getAllCategories();

    public void addCategory(int id, Category category);

    public void removeCategory(int id, int idCategory);

    CategoryDTO findByTag(String tag);

    public CategoryDTO getCategoryById(int id);

    void save(CategoryDTO category);

    void deleteById(int id); //Public redundante en interfaces

    public void saveAll(List<CategoryDTO> categoryDTOS);


    //La persistencia no es responsabilidad de esta interfaz, es del repo.
}