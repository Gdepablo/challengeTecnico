package Backend.services;

import Backend.component.CategoryDTO;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    void addCategory(int id, CategoryDTO category);

    void removeCategory(int id, int idCategory);

    CategoryDTO findByTag(String tag);

    CategoryDTO getCategoryById(int id);

    void save(CategoryDTO category);

    void deleteById(int id);

    void saveAll(List<CategoryDTO> categoryDTOS);
}