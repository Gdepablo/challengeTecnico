package Backend.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="Notes")
public class Note implements Serializable {

    //El serializable segun GPT Ai se usa para transformar en bytes y no se que y (poder) guardar en disco.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    @Size(max = 255)
    private String title;
    @Size(max = 5000) //El size solo se valida al momento de la creacion.
    // Si al actualizar se supera el contenido entonces no pasa nada. O sea, que no limita el update
    private String content;
    private Boolean active = true; // true si esta activo, false si esta archivado
    @ManyToMany(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinTable(name = "note_X_category",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();
    @ManyToOne
    @JsonIgnore //Para que no serialice.No me importa el creador de la nota si al final el usuario sabe que notas tiene.
    private User creator;


    public void addCategory(Category newCategory) {
        this.categories.add(newCategory);
    }

    public void removeCategory(Category categoryForRemoval) {
        this.categories.remove(categoryForRemoval);
    } //Getters y setters esta bien que esten acá.

    public void addCategories(List<Category> newCategories) {
        this.categories.addAll(newCategories);
    }

    public void removeCategories(List<Category> categoriesForRemoval) {
        this.categories.removeAll(categoriesForRemoval);
    }


    //Uso el data de Lombok para evitar poner getters y setters (reduce codigo boilerplate)

    //POJO: Una clase cualquiera que no implementa ni hereda de nada. Por ejemplo la de MHelpers o las service.
    //Y una bean es digamos un componente que spring instancia y maneja al inicio de la app. Suelen ser singletons.

}