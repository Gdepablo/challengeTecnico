package Backend.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="Notes")
public class Note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    @Size(max = 255)
    private String title;
    @Size(max = 5000)
    private String content;
    private Boolean active = true; // true si esta activo, false si esta archivado
    @ManyToMany(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinTable(name = "note_X_category",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();
    @ManyToOne
    @JsonIgnore
    private Client creator;


    public void addCategory(Category newCategory) {
        this.categories.add(newCategory);
    }

    public void removeCategory(Category categoryForRemoval) {
        this.categories.remove(categoryForRemoval);
    }

    public void addCategories(List<Category> newCategories) {
        this.categories.addAll(newCategories);
    }

}