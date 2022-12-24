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
public class Nota {
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
    List<Categoria> category = new ArrayList<>();

    public Nota(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public boolean estaActiva(Nota nota) {
        return nota.getActive();
    }

    public void agregarCategoria(Categoria categoria) {
        category.add(categoria);
    }

    public void quitarCategoria(Categoria categoria) {
        category.remove(categoria);
    } //TODO: Esto no va en notas, va en IServiceImpl o sea la impl del servicio.

    //Uso el data de Lombok para evitar poner getters y setters (reduce codigo boilerplate)
    //TODO:Posible unificar el idioma pero es un detalle
}