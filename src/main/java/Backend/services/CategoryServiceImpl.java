package Backend.services;

import Backend.Exceptions.NotFoundException;
import Backend.Helper.MHelpers;
import Backend.component.Category;
import Backend.component.CategoryDTO;
import Backend.component.Note;
import Backend.component.NoteDTO;
import Backend.repository.CategoryRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Data
public class CategoryServiceImpl implements CategoryService {

    private final NoteServiceImpl noteService;
    private final CategoryRepository categoryRepo;

    @Autowired
    public CategoryServiceImpl(NoteServiceImpl noteService, CategoryRepository categoryRepo) {

        this.noteService = noteService;
        this.categoryRepo = categoryRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {

        Iterable<Category> category = categoryRepo.findAll();
        List<Category> iterableToList = new ArrayList<>();
        category.forEach(iterableToList::add);
        return iterableToList.stream().map(this::convertToCategoryDTO).toList();

            }

    private Note convertToNotes(NoteDTO noteDTO) {
        return MHelpers.modelMapper().map(noteDTO, Note.class);
    }

    private NoteDTO convertToNotesDTO(Note note) {
        return MHelpers.modelMapper().map(note, NoteDTO.class);
    }
            public CategoryDTO convertToCategoryDTO(final Category category) {
                return MHelpers.modelMapper().map(category, CategoryDTO.class);
            }
            public Category convertToCategory(final CategoryDTO categoryDTO) {
                return MHelpers.modelMapper().map(categoryDTO, Category.class);
            }

            @Override
            public void addCategory(int noteID, CategoryDTO category) {
                Note note = convertToNotes(noteService.getNotesById(noteID));
                Category aCategory = convertToCategory(category);
                note.addCategory(aCategory);
                this.save(category);
                NoteDTO noteDTO = convertToNotesDTO(note);
                noteService.save(noteDTO);
                }

    @Override
    public void save(CategoryDTO category) {
        Category newCategory = this.convertToCategory(category);
        this.categoryRepo.save(newCategory);
    }

    @Override
    public void saveAll(List<CategoryDTO> categoryDTOS) {
        List<Category> newCategory = categoryDTOS.stream().map(this::convertToCategory).toList();
        this.categoryRepo.saveAll(newCategory);
    }

    @Override
    public void deleteById(int id) {
        Optional<Category> categoryDTO = categoryRepo.findById(id);
        Category category = this.noOptionalCategory(categoryDTO);
        categoryRepo.delete(category);
    }

    @Override
    public void removeCategory(int id, int idCategory) {
        Note note = this.convertToNotes(noteService.getNotesById(id));
        Category category = this.convertToCategory(this.getCategoryById(idCategory));
        note.removeCategory(category);
        deleteById(idCategory); //No sé si corresponde que al removerla se borre.
        NoteDTO noteToPass = this.convertToNotesDTO(note); //Rompe tanto si no existe categoria como si no existe la nota.
        noteService.save(noteToPass);
    }
    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(int id) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        Optional<CategoryDTO> categoryDTO = categoryOptional.map(this::convertToCategoryDTO); //Mapeo para convertir de Notes a NotesDTO
        return this.noOptionalCategoryDTO(categoryDTO);
    };


    @Override
    @Transactional(readOnly = true)
    public CategoryDTO findByTag(String name) {
       Optional<Category> categoryPure = categoryRepo.findByTag(name);
       return this.convertToCategoryDTO(categoryPure.stream().toList().get(0));
    }

    public CategoryDTO noOptionalCategoryDTO(Optional<CategoryDTO> categoryDTO) {
        if(categoryDTO.isPresent())
            return categoryDTO.get();
        else {
            throw new NotFoundException("Category not found");
        }
    }

    public Category noOptionalCategory(Optional<Category> category) {
        if(category.isPresent())
            return category.get();
        else {
            throw new NotFoundException("Category not found");
        }
    }
    }
