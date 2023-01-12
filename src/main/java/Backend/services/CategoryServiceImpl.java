package Backend.services;

import Backend.Helper.MHelpers;
import Backend.component.Category;
import Backend.component.CategoryDTO;
import Backend.component.Notes;
import Backend.component.NotesDTO;
import Backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    NoteService noteService;
    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public List<CategoryDTO> getAllCategories() {

        Iterable<Category> category = categoryRepo.findAll();
        List<Category> iterableToList = new ArrayList<Category>();
        category.forEach(iterableToList::add);
        List<CategoryDTO> categoriesToRetrieve = iterableToList.stream().map(this::convertToCategoryDTO).toList();
        return categoriesToRetrieve;

    }

    public NotesDTO convertToNotesDTO(final Notes note) {
        return MHelpers.modelMapper().map(note, NotesDTO.class);
    }
    public Notes convertToNotes(final NotesDTO note) {
        return MHelpers.modelMapper().map(note, Notes.class);
    }
    public CategoryDTO convertToCategoryDTO(final Category category) {
        return MHelpers.modelMapper().map(category, CategoryDTO.class);
    }
    public Category convertToCategory(final CategoryDTO categoryDTO) {
        return MHelpers.modelMapper().map(categoryDTO, Category.class);
    }

    @Override
    public void addCategory(int noteID, Category category) {
       Notes note = this.convertToNotes(noteService.getNotesById(noteID));
       note.addCategory(category);
       NotesDTO noteToPass = this.convertToNotesDTO(note);
       noteService.save(noteToPass);
    }

    @Override
    public void removeCategory(int id, Category category) {
        Notes note = this.convertToNotes(noteService.getNotesById(id));
        note.removeCategory(category);
        NotesDTO noteToPass = this.convertToNotesDTO(note);
        noteService.save(noteToPass);
    }

    @Override
    public CategoryDTO findByTag(String name) {
       Optional<Category> categoryPure = categoryRepo.findByTag(name);
       return this.convertToCategoryDTO(categoryPure.stream().toList().get(0));
    }

}