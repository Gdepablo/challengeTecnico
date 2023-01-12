package Backend.component;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name="Notes")
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    int id;
    @Size(max = 255)
    String title;
    @Size(max = 5000)
    String content;
    Boolean active = true; // true si está activo, false si está archivado
    @OneToMany
    List<Category> categories = new ArrayList<>();

    public Notes(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public boolean estaActiva(Notes nota) {
        return nota.getActive();
    }

    public void addCategory(Category categoria) {
        categories.add(categoria);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    } //Getters y setters está bien que estén acá.

    public void addCategories(List<Category> categories) {
        categories.addAll(categories);
    }

    public void removeCategories(List<Category> categories) {
        categories.removeAll(categories);
    }


    //Uso el data de Lombok para evitar poner getters y setters (reduce codigo boilerplate)
    //TODO:Posible unificar el idioma pero es un detalle
}