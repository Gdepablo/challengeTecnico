package Backend.services;

import Backend.component.Category;
import Backend.component.CategoryDTO;
import Backend.component.Notes;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface CategoryService {
    //https://www.quora.com/Why-dont-you-have-to-implement-all-the-methods-in-an-interface-if-you-are-injecting-a-repository-class-in-Spring-Boot

    List<CategoryDTO> getAllCategories();

    public void addCategory(int id, Category category);

    public void removeCategory(int id, Category category);

    CategoryDTO findByTag(String tag);

    //La persistencia no es responsabilidad de esta interfaz, es del repo.
}